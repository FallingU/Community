package life.fallingu.commuity.commuity.controller;

import life.fallingu.commuity.commuity.dto.QuestionDTO;
import life.fallingu.commuity.commuity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id,
                           Model model){
        QuestionDTO question = questionService.findById(id);
        model.addAttribute("question",question);
        return "question";
    }
}
