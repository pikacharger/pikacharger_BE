package elice04_pikacharger.pikacharger.security.oauth.handler;


import elice04_pikacharger.pikacharger.domain.user.entity.Role;
import elice04_pikacharger.pikacharger.security.jwt.JwtUtil;
import elice04_pikacharger.pikacharger.security.jwt.MyTokenPayload;
import elice04_pikacharger.pikacharger.security.oauth.CustomOAuth2User;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try{
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            MyTokenPayload payload = new MyTokenPayload(oAuth2User.getUserId(),oAuth2User.getEmail(), oAuth2User.getName(), oAuth2User.getRoles());
            if(oAuth2User.getRoles().contains(Role.GUEST)){
                String accessToken = jwtUtil.generateToken(payload);
                response.addHeader(jwtUtil.getAccessHeader(),"Bearer " + accessToken);
                //response.sendRedirect("oauth/sign-up");

                jwtUtil.sendAccessAndRefreshToken(response,accessToken,null);
            } else{
                loginSuccess(response,oAuth2User);
            }
        } catch (Exception e){
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException, ServletException {
        MyTokenPayload payload = new MyTokenPayload(oAuth2User.getUserId(),oAuth2User.getEmail(), oAuth2User.getName(), oAuth2User.getRoles());
        String accessToken = jwtUtil.generateToken(payload);
        String refreshToken = jwtUtil.generateRefreshToken();
        response.addHeader(jwtUtil.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtUtil.getRefreshHeader(), "Bearer " + refreshToken);

        jwtUtil.sendAccessAndRefreshToken(response,accessToken,refreshToken);
        jwtUtil.updateRefreshToken(oAuth2User.getEmail(),refreshToken);
    }
}
