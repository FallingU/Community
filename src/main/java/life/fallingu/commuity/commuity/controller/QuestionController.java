package life.fallingu.commuity.commuity.controller;

import life.fallingu.commuity.commuity.dto.CommentDTO;
import life.fallingu.commuity.commuity.dto.QuestionDTO;
import life.fallingu.commuity.commuity.enums.CommentTypeEnum;
import life.fallingu.commuity.commuity.pojo.User;
import life.fallingu.commuity.commuity.service.CommentService;
import life.fallingu.commuity.commuity.service.NotificationService;
import life.fallingu.commuity.commuity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class QuestionController {
    @Autowired
    NotificationService notificationService;
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Long id,
                           Model model,
                           HttpSession session){
        questionService.incr(id);
        QuestionDTO question = questionService.findById(id);
        List<QuestionDTO> relatedQuestions=questionService.getRelateList(question);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION.getType());
        User user = (User)session.getAttribute("user");
        if(user!=null) {
            int unreadCount = notificationService.unreadCount(user.getId());
            System.out.println(unreadCount);
            session.setAttribute("unreadCount", unreadCount);
        }
        model.addAttribute("question",question);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
