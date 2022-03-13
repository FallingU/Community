package life.fallingu.commuity.commuity.interceptor;

import life.fallingu.commuity.commuity.pojo.Notification;
import life.fallingu.commuity.commuity.pojo.User;
import life.fallingu.commuity.commuity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionInterceptor implements HandlerInterceptor {

    @Value("${github.redirect.uri}")
    private String githubRedirectUri;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.getServletContext().setAttribute("githubRedirectUri", githubRedirectUri);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user==null){

            response.sendRedirect("/");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
