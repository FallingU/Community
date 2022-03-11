package life.fallingu.commuity.commuity.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    //分页查询
    public PageInfo<QuestionDTO> list(Integer page, Integer size) {
        PageHelper.startPage(page,size);
       List<QuestionDTO> list =  questionMapper.list();
//       System.out.println(list.size());
//       List<QuestionDTO> res = new ArrayList<>();
//       for(Question question : list){
//           User user = userMapper.findUserById(question.getCreator());
//           QuestionDTO questionDTO = new QuestionDTO();
//           BeanUtils.copyProperties(question,questionDTO);
//           questionDTO.setUser(user);
//           res.add(questionDTO);
//       }
       PageInfo<QuestionDTO> pageInfo = new PageInfo<>(list,5);
       return pageInfo;
    }

    public PageInfo<QuestionDTO> list(Integer id, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<QuestionDTO> list = questionMapper.listByUserId(id);
        return new PageInfo<>(list,5);
    }
}
