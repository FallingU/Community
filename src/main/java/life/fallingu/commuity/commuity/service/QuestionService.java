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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class QuestionService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;

    //分页查询
    public PageInfo<QuestionDTO> list(String search,Integer page, Integer size) {
        PageHelper.startPage(page,size);
        if(search!=null&&"".equals(search)) {
            String str = search.trim().replaceAll(" ", "|");
            search = str;
        }
        List<QuestionDTO> list =  questionMapper.listBySearch(search);
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

    public PageInfo<QuestionDTO> list(Long id, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<QuestionDTO> list = questionMapper.listByUserId(id);
        return new PageInfo<>(list,5);
    }

    public QuestionDTO findById(Long id) {
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
    public void incr(Long id) {
        int res = questionMapper.incrById(id);
        if(res==0){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
    }

    /**
     * 获取相关问题
     * @param question
     * @return
     */
    public List<QuestionDTO> getRelateList(QuestionDTO question) {
        String tags = question.getTag();
        String replace = StringUtils.replace(tags, ",", "|");
        Question ques  = new Question();
        ques.setId(question.getId());
        ques.setTag(replace);
        return questionMapper.getRelateList(ques);
    }
}
