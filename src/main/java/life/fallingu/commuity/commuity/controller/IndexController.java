package life.fallingu.commuity.commuity.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.fallingu.commuity.commuity.dto.QuestionDTO;
import life.fallingu.commuity.commuity.mapper.UserMapper;
import life.fallingu.commuity.commuity.pojo.User;
import life.fallingu.commuity.commuity.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    /**
     * 首页显示文章数据
     * @param token
     * @param session
     * @param model
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/")
    public String index(@CookieValue(name = "token",required = false)String token,
                        HttpSession session,
                        Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "8")Integer size){
        if("".equals(token)||token==null||token.length()==0){

        }else{
            User user=userMapper.findUserByToken(token);
            if(user!=null){
                session.setAttribute("user",user);
            }
        }
        PageInfo<QuestionDTO> pageInfo = questionService.list(page, size);
        model.addAttribute("pageInfo",pageInfo);
        return "index";
    }
}
