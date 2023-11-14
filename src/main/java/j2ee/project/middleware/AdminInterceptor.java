package j2ee.project.middleware;

//admin middleware

import j2ee.project.controller.Admin.AccountController;
import j2ee.project.ultils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Array;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    //exclude path
    private static final String[] excludePath = {
            "/api/admin/account/login",
            "/api/admin/account/check-token",
            "/api/admin/product/getAll",
    };
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //check token in header
        if (isExcludePath(request,request.getRequestURI())) {
            return true;
        }
        String token = request.getHeader("Admin-Token");
        boolean isValidToke = (ultils.isValidToken(token));
        if (!isValidToke) {
            response.setStatus(401);
            return false;
        }

        return true;
    }

    private boolean isExcludePath(HttpServletRequest request,String path) {
        for (String p : excludePath) {
            if (p.equals(path)) {
                return true;
            }
        }

        //api regex
        if (request.getMethod().equals("GET") ) {
            if (path.contains("/api/admin/product")) {
                return true;
            }
        }



        return false;
    }
}
