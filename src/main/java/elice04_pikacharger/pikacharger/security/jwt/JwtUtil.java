package elice04_pikacharger.pikacharger.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import elice04_pikacharger.pikacharger.domain.user.entity.Role;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.TokenRepository;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.mail.search.SearchTerm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Getter
@RequiredArgsConstructor
public class JwtUtil {
    private final TokenRepository tokenRepository;
    //jwt 형성을 위한 비밀 키
    @Value("${jwt.secret}")
    private String secret;

    //jwt가 생성되었을 때 얼마나 유효하게 지속되는지
    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    //secret이라는 비밀 키를 기반으로 실제로 암호화된 secretKey
    private SecretKey secretKey;

    private final UserRepository userRepository;

    public final String[] allowedUrls = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/",
            "/user/**",
            "/error",
            "/index.html",
            "/swagger-resources",
            "/swagger-resources/**",
            "/webjars/**"

    };

    @PostConstruct
    public void init() {
        // 비밀 키를 바이트 배열로 변환하고, HMAC SHA-512로 암호화 키를 생성합니다.
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    //AccessToken을 생성해주는 함수
    public String generateToken(MyTokenPayload payload) {
        //현재 시간 + 아까 설정한 expiration을 더해서 이 토큰의 생존 시간을 보장함
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        //사용자를 식별하는 claim으로 email, username, role을 사용
        return Jwts.builder()
                .claim("userId", payload.getUserId())
                .claim("email",payload.getEmail())
                .claim("username",payload.getName())
                .claim("roles",payload.getRoles())
                .setIssuedAt(now) //token이 생성되는 이슈가 발생된 첫 시점
                .setExpiration(expiryDate) //token의 생존기간 설정
                .signWith(secretKey) //아까 암호화 한 secretKey를 이용해서 JWT를 만든다 이거임
                .compact();
    }
    //RefreshToken 생성하는 함수
    //굳이 Claim을 가질 필요가 없음. 왜? 어차피 리프레시토큰 줘놓고 나중에 얘랑 비교해서 같으면 새로 generateToken해주면 되니깐요.
    public String generateRefreshToken(){
        Date now = new Date();
        return JWT.create()
                .withSubject("RefreshToken")
                .withExpiresAt(new Date(now.getTime() + expiration))
                .sign(Algorithm.HMAC512(secret));
    }
    //실컷 AccessToken 만들었으니까 이제 헤더에 실어서 보내야지
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
    }
    //AccessToken이랑 RefreshToken 함께 헤더로 보내버리기.
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken,String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, refreshToken);
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith("Bearer "))
                .map(accessToken -> accessToken.replace("Bearer ",""));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
            return Optional.ofNullable(request.getHeader(refreshHeader))
                    .filter(refreshToken -> refreshToken.startsWith("Bearer "))
                    .map(refreshToken -> refreshToken.replace("Bearer ",""));
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }

    public void updateRefreshToken(String email, String refreshToken) {
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }

    public Optional<String> extractEmail(String accessToken){
        try{
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(accessToken)
                    .getClaim("email")
                    .asString());
        } catch (Exception e){
            return Optional.empty();
        }
    }


    //유효한 Token인지 아닌지 검증하는 함수, 리턴값 true/false
    public boolean validateToken(String token) {
        try {
            //JWT를 분석하는데, 애초에 만들 때 secretKey를 이용해서 서명을 했으니, 얘로 다시 서명이 유효한지 검증
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            return false;
        }
    }

    //파라미터로 받은 token이 유효한 토큰인지 확인하는 함수
    //리턴 값으로 email, username, role을 포함한 DTO가 반환된다.
    public MyTokenPayload verify(String token) throws BadCredentialsException {
        try{
            //parse:분석하다, 즉 JWT를 분석하겠다 이거임
            Claims payload = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            //payload(DTO)에는 JWT를 분석했을 때 generateToken을 할 때 사용된 claim과 관련된 정보를 담고 있음.
            Long userId = payload.get("userId", Long.class);
            String email = payload.get("email", String.class);
            String username = payload.get("username", String.class);
            List<Role> roleList = payload.get("roles", List.class);
            Set<Role> roles = new HashSet<>(roleList);

            MyTokenPayload myTokenPayload = new MyTokenPayload(userId,email,username,roles);
            return myTokenPayload;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("Invalid token");
        }
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }


// 토큰 만료일 받아오는 거랑, 유저 아이디 뽑아오는 거 따로따로
    public Long extractUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody();

        return claims.get("userId", Long.class);
    }

    public Date extractExpirationDateFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token).getBody();
        return claims.getExpiration();  // 만료 시간 반환
    }


    public String extractJwtFromRequest(HttpServletRequest request) {
        // Extract JWT from the Authorization header
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public String extractRefreshTokenFromRequest(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh");
        if (StringUtils.hasText(refreshToken)){
            return refreshToken;
        }
        return null;
    }

    public String deleteRefreshByEmail(String userEmail) {
        try{
            tokenRepository.deleteByEmail(userEmail);
        } catch (Exception e){
            throw e;
        }
        return "성공적으로 로그아웃 하였습니다.";
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
