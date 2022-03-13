package life.fallingu.commuity.commuity.controller;

import com.github.pagehelper.PageInfo;
import life.fallingu.commuity.commuity.dto.NotificationDTO;
import life.fallingu.commuity.commuity.dto.QuestionDTO;
import life.fallingu.commuity.commuity.pojo.User;
import life.fallingu.commuity.commuity.service.NotificationService;
import life.fallingu.commuity.commuity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;
    /**
     * 用户查看自己发布的问题
     * @param action
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/profile/{action}")
    public String doAction(@PathVariable("action")String action,
                           Model model,
                           HttpSession session,
                           @RequestParam(value = "page",defaultValue = "1") Integer page,
                           @RequestParam(value = "size",defaultValue = "8")Integer size){
        User user = (User) session.getAttribute("user");
        if("questions".equals(action)){
            model.addAttribute("sectionName","我的问题");
            model.addAttribute("section","questions");
            PageInfo<QuestionDTO> pageInfo =questionService.list(user.getId(),page,size);
            model.addAttribute("pageInfo",pageInfo);
        }else if("replies".equals(action)){
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("section","replies");
            PageInfo<NotificationDTO> pageInfo = notificationService.list(user.getId(),page,size);
            model.addAttribute("pageInfo",pageInfo);
        }
        return "/profile";
    }
}
