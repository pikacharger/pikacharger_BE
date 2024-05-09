package elice04_pikacharger.pikacharger.security.jwt;

import elice04_pikacharger.pikacharger.domain.user.entity.CustomUserDetails;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Arrays.stream(jwtUtil.allowedUrls).anyMatch(item -> item.equalsIgnoreCase(request.getServletPath()));
    }

    //내부적으로 JWT token에 대한 Filtering
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.extractJwtFromRequest(request);

        if (token != null && !StringUtils.isEmpty(token)) {
            if (jwtUtil.validateToken(token)) {
                MyTokenPayload myTokenPayload = jwtUtil.verify(token);
                if (ObjectUtils.isNotEmpty(myTokenPayload)) {
                    CustomUserDetails customUserDetails = new CustomUserDetails(myTokenPayload);

                    SecurityContextHolder.getContext().setAuthentication(customUserDetails);
                }
            } else {
                response.setStatus(401);
            }
        }

        //최종 목적지 도달: 필터 체인의 마지막 필터가 doFilter 메서드를 호출하면, 요청은 최종적으로 대상 서블릿이나 컨트롤러에 도달하게 됩니다. 이곳에서 실제 비즈니스 로직이 처리되고, 응답이 생성됩니다.
        filterChain.doFilter(request, response);
    }


    public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        userRepository.findByRefreshToken(refreshToken)
                .ifPresent(user ->{
                    MyTokenPayload payload = new MyTokenPayload(user.getId(),user.getEmail(),user.getUsername(),user.getRoles());
                    String reIssuedRefreshToken = reIssueRefreshToken(user);
                    jwtUtil.sendAccessAndRefreshToken(response, jwtUtil.generateToken(payload),reIssuedRefreshToken);
                });
    }

    public String reIssueRefreshToken(User user){
        String reIssuedRefreshToken = jwtUtil.generateRefreshToken();
        user.updateRefreshToken(reIssuedRefreshToken);
        userRepository.saveAndFlush(user);
        return reIssuedRefreshToken;
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtUtil.extractAccessToken(request)
                .filter(jwtUtil::validateToken)
                .ifPresent(accessToken -> jwtUtil.extractEmail(accessToken)
                        .ifPresent(email -> userRepository.findByEmail(email)
                                .ifPresent(this::saveAuthentication)));
        filterChain.doFilter(request,response);
    }

    private void saveAuthentication(User myUser){
        String password = myUser.getPassword();
        if(password == null){
            //이렇게 하는 이유는 소셜로그인을 통해 회원가입을 한 애들은 db에 password가 null값이기 때문이다.
            password = PasswordUtil.generateRandomPassword();
        }

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(password)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsUser, null, userDetailsUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }



}
