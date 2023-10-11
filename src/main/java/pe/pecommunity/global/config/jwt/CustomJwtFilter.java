package pe.pecommunity.global.config.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@RequiredArgsConstructor
public class CustomJwtFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    /**
     * JWT
     * 1. 사용자의 request Header에서 토큰을 가져옴
     * 2. Token의 유효성 검사를 실시
     * 3. 유효하면 Authentication 인증객체 생성
     * 4. SecurityContext에 저장
     * 5. 해당 Filter 과정이 끝나면 시큐리티에서 다음 Filter로 이동함
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = tokenProvider.resolveToken((HttpServletRequest) request);

        // 유효성 검증
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            // SecurityContext에 Authentication 객체(토큰 인증과정 결과)를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context에 '{}' 인증 정보를 저장했습니다", authentication.getName());
        } else {
            log.debug("유효한 JWT 토큰이 없습니다");
        }

        chain.doFilter(request, response);
    }
}
