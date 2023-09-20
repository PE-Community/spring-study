package pe.pecommunity.global.config.jwt;

import static pe.pecommunity.global.error.ErrorCode.TOKEN_INVALID_EXCEPTION;
import static pe.pecommunity.global.error.ErrorCode.TOKEN_UNSUPPORTED_EXCEPTION;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import pe.pecommunity.global.common.response.ApiResponse;
import pe.pecommunity.global.common.response.ResponseStatus;
import pe.pecommunity.global.error.ErrorCode;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    /**
     * 토큰 관련 에러 핸들링
     * JwtTokenFilter 에서 발생하는 에러를 핸들링해준다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            // 토큰
            setErrorResponse(request, response, e);
        }
    }

    private void setErrorResponse(HttpServletRequest request, HttpServletResponse response, Throwable ex){
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        error.put("message", ex.getMessage());
        error.put("path", request.getServletPath());
        data.put("error", error);

        ApiResponse errorResponse = new ApiResponse(ResponseStatus.ERROR, data, "Unauthorized");

        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
