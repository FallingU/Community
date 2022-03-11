package life.fallingu.commuity.commuity.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.fallingu.commuity.commuity.dto.QuestionDTO;
import life.fallingu.commuity.commuity.exception.CustomizeErrorCode;
import life.fallingu.commuity.commuity.exception.CustomizeException;
import life.fallingu.commuity.commuity.mapper.QuestionMapper;
import life.fallingu.commuity.commuity.mapper.UserMapper;
import life.fallingu.commuity.commuity.pojo.Question;
import life.fallingu.commuity.commuity.pojo.QuestionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public QuestionDTO findById(Integer id) {
       QuestionDTO question = questionMapper.findById(id);
        return question;
    }

    public void insertOrUpdate(Question question) {
        if(question.getId()!=null){
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setTitle(question.getTitle());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion,example);
        }else{
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        }
    }

    /**
     * 增加浏览数
     * @param id
     */
    public void incr(Integer id) {
        int res = questionMapper.incrById(id);
        if(res==0){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
    }
}
