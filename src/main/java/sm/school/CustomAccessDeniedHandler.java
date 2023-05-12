package sm.school;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private AntPathMatcher pathMatcher = new AntPathMatcher();


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        String requestUri = request.getRequestURI();

        if (pathMatcher.match("/study/**", requestUri) ||
                pathMatcher.match("/studyMember/**", requestUri) ||
                pathMatcher.match("/meeting/**", requestUri) ||
                pathMatcher.match("/meetingPro/**", requestUri)) {
            response.sendRedirect("/member/login");
        }else {
            response.sendRedirect("/accessBlock");
        }
    }
}
