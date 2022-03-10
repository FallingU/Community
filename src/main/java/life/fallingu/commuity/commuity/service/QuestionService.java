package life.fallingu.commuity.commuity.service;

import life.fallingu.commuity.commuity.dto.QuestionDTO;
import life.fallingu.commuity.commuity.mapper.QuestionMapper;
import life.fallingu.commuity.commuity.mapper.UserMapper;
import life.fallingu.commuity.commuity.pojo.Question;
import life.fallingu.commuity.commuity.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;


    public List<QuestionDTO> list() {
       List<Question> list =  questionMapper.list();
       List<QuestionDTO> res = new ArrayList<>();
       for(Question question : list){
           User user = userMapper.findUserById(question.getCreator());
           QuestionDTO questionDTO = new QuestionDTO();
           BeanUtils.copyProperties(question,questionDTO);
           questionDTO.setUser(user);
           res.add(questionDTO);
       }
       return res;
    }
}
