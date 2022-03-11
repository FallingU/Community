package life.fallingu.commuity.commuity.controller;

import com.sun.deploy.net.HttpResponse;
import life.fallingu.commuity.commuity.dto.AccessTokenDTO;
import life.fallingu.commuity.commuity.dto.GithubUser;
import life.fallingu.commuity.commuity.mapper.UserMapper;
import life.fallingu.commuity.commuity.pojo.User;
import life.fallingu.commuity.commuity.provider.GithubProvider;
import life.fallingu.commuity.commuity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;


@Controller
public class AuthController {

    @Autowired
    GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;

    @Autowired
    UserService userService;



    @RequestMapping("/login")
    public String login(){
        return "redirect:https://github.com/login/oauth/authorize?client_id=c8339e61596f47fd7a9d&redirect_uri=http://localhost:8080/callback&scope=user&state=1";
    }

    /**
     * github将用户请求重定向到服务器的callback请求路径下,处理用户登录
     * · 用户点击登录按钮，用户被重定向到github的授权身份的url上，并且带有社区的client_id,和获得授权后的回调，和作用域参数和一个状态参数。
     * · 用户授权后，通过上一次传过去的回调地址，github将用户重定向回服务器的站点中，并带有code参数和state参数
     * · 服务器通过code参数和state参数再向github发送post请求access_token(访问令牌),需要传入client_id参数，client_secret参数,code参数
     * · 然后github响应返回用户的access_token,再向github发送请求，在请求头中携带该access_token信息，获取用户信息
     * · github验证access_token信息并返回user信息
     *github授权登录后获取到了用户授权的信息
     * · 将用户授权的信息封装到一个User实体中，User实体中有一个token属性，为User生成一个UUID作为持久化登录的token
     * · 将用户插入到数据库User表中
     * · 使用response对象中添加一个cookie为token=刚刚生成的UUID让客户端浏览器存储token信息。
     * · 重定向到首页
     * · 此时用户请求头中的cookie中就有token信息，根据token信息查询数据库中user表，如果查询出来的用户不为空则说明是已登录状态，这样就实现了在cookie超时时间内的持久化登录。
     * http://localhost:8080/callback?code=7375be889eafe9d66ae1&state=1
     * @return
     */
    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO =
                new AccessTokenDTO(
                        clientId,
                        clientSecret,
                        code,
                        null);
        //获取用户token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //根据用户token取github上获取用户信息
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null&&githubUser.getId()!=null){
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.insertOrUpdate(user);
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60*60*24*7);
            response.addCookie(cookie);
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }

    /**
     * 退出：
     * · 用户点击退出登录，服务器端再session中删除user属性，
     * · response对象通过setcookie给一个空值覆盖原token数据并且生命期为0
     * @param session
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session,
                         HttpServletResponse response){
        session.removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
