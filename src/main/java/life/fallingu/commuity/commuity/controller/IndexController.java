package life.fallingu.commuity.commuity.controller;

import life.fallingu.commuity.commuity.dto.QuestionDTO;
import life.fallingu.commuity.commuity.mapper.UserMapper;
import life.fallingu.commuity.commuity.pojo.User;
import life.fallingu.commuity.commuity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
    @RequestMapping("/")
    public String index(@CookieValue(name = "token",required = false)String token,
                        HttpSession session,
                        Model model){
        if("".equals(token)||token==null||token.length()==0){

        }else{
            User user=userMapper.findUserByToken(token);
            if(user!=null){
                session.setAttribute("user",user);
            }
        }
        List<QuestionDTO> questions = questionService.list();
        System.out.println(questions.size());
        model.addAttribute("questions",questions);
        return "index";
    }
}
