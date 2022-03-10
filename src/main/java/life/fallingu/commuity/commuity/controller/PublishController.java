package life.fallingu.commuity.commuity.controller;

import life.fallingu.commuity.commuity.mapper.QuestionMapper;
import life.fallingu.commuity.commuity.pojo.Question;
import life.fallingu.commuity.commuity.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    /**
     * 发布文章
     * 接收到发布文章的请求
     * · 先判断用户是否登录，如果用户没登陆则提醒用户进行登录
     * · 将前端发送来的文章数据封装到实体类中，判断数据是否完整，如果缺少内容则返回错误信息并转发到文章发布页面，提醒用户错误信息。
     * · 如果数据完成，结合数据库对文章实体类的属性进行完善
     * · 将文章数据插入到数据库中，
     * · 并重定向到首页
     * @param question
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/publish")
    public String doPublish(Question question, HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        model.addAttribute("question",question);
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "/publish";
        }
        if(question.getTitle()==null||"".equals(question.getTitle())){
            model.addAttribute("error","标题不能为空");
            return "/publish";
        }

        if(question.getDescription()==null||"".equals(question.getDescription())){
            model.addAttribute("error","问题描述不能为空");
            return "/publish";
        }
        if(question.getTag()==null||"".equals(question.getTag())){
            model.addAttribute("error","标签不能为空");
            return "/publish";
        }
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        //向数据库插入文章数据
        questionMapper.insert(question);
        return "redirect:/";
    }
}
