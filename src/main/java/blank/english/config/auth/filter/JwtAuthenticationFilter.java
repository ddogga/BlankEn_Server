package blank.english.config.auth.filter;


import blank.english.config.auth.token.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/*
* 클라이언트 요청 시 JWT 인증을 하기 위해 설치하는 Custom Filter로 UsernamePasswordAuthenticationFilter이전에 실행
* JwtTokenProvider가 검증을 끝내고 나면 JWT로 유저 정보를 조회해와서 UsernamePasswordAuthenticationFilter로 유저정보를 전달
*/

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) request);

        // 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            //인증 객체 만들기. 인증 객체는 반드시 Authentication의 구현체만 가능.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            //인증에 성공하면 Spring이 관리하는 SecurityContext에 인증 객체를 설정해 준다.
            //Security를 사용한 인증과 사용하지 않은 인증과의 차이
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        chain.doFilter(request, response);
    }

    // 헤더에서 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");//클라이언트의 요청 헤더안에 있는 Authorization 데이터를 추출
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
