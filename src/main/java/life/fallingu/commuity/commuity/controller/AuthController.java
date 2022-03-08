package life.fallingu.commuity.commuity.controller;

import life.fallingu.commuity.commuity.dto.AccessTokenDTO;
import life.fallingu.commuity.commuity.dto.GithubUser;
import life.fallingu.commuity.commuity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {

    @Autowired
    GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;

    @RequestMapping("/login")
    public String login(){
        return "redirect:https://github.com/login/oauth/authorize?client_id=c8339e61596f47fd7a9d&redirect_uri=http://localhost:8080/callback&scope=user&state=1";
    }

    /**
     * github将用户请求重定向到服务器的callback请求路径下
     *
     * http://localhost:8080/callback?code=7375be889eafe9d66ae1&state=1
     * @return
     */
    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO =
                new AccessTokenDTO(
                        clientId,
                        clientSecret,
                        code,
                        null);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user);
        return "redirect:/";
    }

}
