package blank.english.config;

import blank.english.config.auth.filter.JwtAuthenticationFilter;
import blank.english.config.auth.handler.OAuth2AuthenticationFailureHandler;
import blank.english.config.auth.handler.OAuth2AuthenticationSuccessHandler;
import blank.english.config.auth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import blank.english.config.auth.service.CustomOAuth2UserService;
import blank.english.config.auth.token.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final JwtTokenProvider jwtTokenProvider;


    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler, OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler, JwtTokenProvider jwtTokenProvider) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    /*
     * 쿠키 기반 인가 Repository
     * 인가 응답을 연계 하고 검증할 때 사용.
     * */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }


    @Bean
    public SecurityFilterChain  filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .anyRequest().permitAll()
//			  .antMatchers("/**").authenticated() // 인가된 사용자만 접근 가능하도록 설정
//			  .antMatchers("게시물등").hasRole(Role.USER.name()) // 특정 ROLE을 가진 사용자만 접근 가능하도록 설정
                .and()
                    .oauth2Login()
                    .authorizationEndpoint().baseUri("/oauth2/authorize")
                    .authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository())
                .and()
                    .redirectionEndpoint()
                    .baseUri("/login/oauth2/code/**")
                .and()
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
