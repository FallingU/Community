package life.fallingu.commuity.commuity.controller;

import life.fallingu.commuity.commuity.mapper.UserMapper;
import life.fallingu.commuity.commuity.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @RequestMapping("/")
    public String index(@CookieValue(name = "token",required = false)String token, HttpSession session){
        if("".equals(token)||token==null||token.length()==0){

        }else{
            User user=userMapper.findUserByToken(token);
            if(user!=null){
                session.setAttribute("user",user);
            }
        }
        return "index";
    }
}
