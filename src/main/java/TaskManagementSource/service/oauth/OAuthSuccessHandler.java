package TaskManagementSource.service.oauth;

import TaskManagementSource.service.interfaces.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final AuthenticationService authenticationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        if(authentication instanceof OAuth2AuthenticationToken token){
            OAuth2User user = token.getPrincipal();
            String username = user.getAttribute("username");
            String email = user.getAttribute("email");
            authenticationService.createUserIfNotExist(username, email);
            authentication = authenticationService.login(username);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
