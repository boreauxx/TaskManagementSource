package TaskManagementSource.config;

import TaskManagementSource.repository.UserRepository;
import TaskManagementSource.service.oauth.OAuthSuccessHandler;
import TaskManagementSource.service.session.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, OAuthSuccessHandler oAuthSuccessHandler) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers("/", "/users/register", "/users/login", "/users/login-error", "/about").permitAll()
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        .requestMatchers("/images/**").permitAll()
                                        .requestMatchers("/error").permitAll()
                                        .anyRequest().authenticated()
                ).formLogin(
                        formLogin ->
                                formLogin
                                        .loginPage("/users/login")
                                        .usernameParameter("username")
                                        .passwordParameter("password")
                                        .defaultSuccessUrl("/home")
                                        .failureForwardUrl("/users/login-error")
                ).oauth2Login(
                        oauth ->
                                oauth
                                        .successHandler(oAuthSuccessHandler)
                                        .failureHandler((request, response, exception) -> {
                                            response.sendRedirect("/users/login-error");
                                        })
                ).logout(
                        logout ->
                                logout
                                        .logoutUrl("/users/logout")
                                        .logoutSuccessUrl("/")
                                        .invalidateHttpSession(true)
                ).rememberMe(
                        rememberMe ->
                                rememberMe
                                        .key("sfg-key")
                                        .rememberMeParameter("remember-me")
                                        .rememberMeCookieName("remember-me")
                                        .tokenValiditySeconds(84600)
                ).build();
    }
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new AppUserDetailsService(userRepository);
    }
}

