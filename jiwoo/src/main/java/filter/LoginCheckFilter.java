package filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import session.SessionConst;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "/member*", "/board*"};
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            if(isLoginCheckPath(requestURI)){
                HttpSession session = httpRequest.getSession(false);
                if(session==null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null) {
                    httpResponse.sendRedirect("/member/login?redirectURL="+requestURI);
                    return;
                }
            }
            chain.doFilter(request,response);
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
