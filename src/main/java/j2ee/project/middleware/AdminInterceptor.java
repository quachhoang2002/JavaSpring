package j2ee.project.middleware;

//admin middleware

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor  {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //check token in header
        String token = request.getHeader("token");
        if(token == null || !token.equals("admin")){
//            response.setStatus(401);
            return true;
        }

        return true;
    }
}
