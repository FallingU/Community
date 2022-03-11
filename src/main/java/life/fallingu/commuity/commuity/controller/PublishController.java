package life.fallingu.commuity.commuity.controller;

import life.fallingu.commuity.commuity.dto.QuestionDTO;
import life.fallingu.commuity.commuity.mapper.QuestionMapper;
import life.fallingu.commuity.commuity.pojo.Question;
import life.fallingu.commuity.commuity.pojo.User;
import life.fallingu.commuity.commuity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id")Integer id,
                       Model model){
        QuestionDTO question = questionService.findById(id);
        model.addAttribute("question",question);
        return "publish";
    }

    /**
     * 发布文章或编辑文章
     * 接收到发布文章的请求
     * · 只有登录了的用户能进入，没登陆用户会被拦截器拦截
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
    public String doPublish(Question question,
                            HttpSession session,
                            Model model){
        User user = (User) session.getAttribute("user");
        model.addAttribute("question",question);
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
        questionService.insertOrUpdate(question);
        return "redirect:/";
    }
}
