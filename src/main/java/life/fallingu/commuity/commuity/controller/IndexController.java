package life.fallingu.commuity.commuity.controller;

import com.github.pagehelper.PageInfo;
import life.fallingu.commuity.commuity.dto.QuestionDTO;
import life.fallingu.commuity.commuity.mapper.UserMapper;
import life.fallingu.commuity.commuity.pojo.User;
import life.fallingu.commuity.commuity.pojo.UserExample;
import life.fallingu.commuity.commuity.service.NotificationService;
import life.fallingu.commuity.commuity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;

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
                        @RequestParam(value = "size",defaultValue = "8")Integer size,
                        @RequestParam(value = "search",required = false)String search){
        if("".equals(token)||token==null||token.length()==0){

        }else{
            UserExample example = new UserExample();
            example.createCriteria().andTokenEqualTo(token);
            List<User> users = userMapper.selectByExample(example);
            if(users.size()!=0){
                session.setAttribute("user",users.get(0));
                int unreadCount = notificationService.unreadCount(users.get(0).getId());
                session.setAttribute("unreadCount",unreadCount);
            }
        }

        PageInfo<QuestionDTO> pageInfo = questionService.list(search,page, size);
        model.addAttribute("pageInfo",pageInfo);
        return "index";
    }
}
