package life.fallingu.commuity.commuity.service;

import life.fallingu.commuity.commuity.dto.CommentDTO;
import life.fallingu.commuity.commuity.enums.CommentTypeEnum;
import life.fallingu.commuity.commuity.exception.CustomizeErrorCode;
import life.fallingu.commuity.commuity.exception.CustomizeException;
import life.fallingu.commuity.commuity.mapper.CommentMapper;
import life.fallingu.commuity.commuity.mapper.NotificationMapper;
import life.fallingu.commuity.commuity.mapper.QuestionMapper;
import life.fallingu.commuity.commuity.pojo.Comment;
import life.fallingu.commuity.commuity.pojo.Notification;
import life.fallingu.commuity.commuity.pojo.Question;
import life.fallingu.commuity.commuity.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    NotificationMapper notificationMapper;
    public void insert(Comment comment, User user) {
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null|| !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getContent()==null||"".equals(comment.getContent())){
            throw new CustomizeException(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        if(comment.getType()==CommentTypeEnum.QUESTION.getType()){
            //回复帖子
            Question dbquestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(dbquestion==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setGmtModified(comment.getGmtCreate());
            createNotify(user,dbquestion.getCreator(),1,dbquestion);
            commentMapper.insert(comment);
            questionMapper.incrCommentById(dbquestion.getId());
        }else{
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbcomment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Question question = questionMapper.selectByPrimaryKey(dbcomment.getParentId());
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setGmtModified(comment.getGmtCreate());
            createNotify(user,dbcomment.getCommentator(),2,question);
            commentMapper.insert(comment);
        }
    }

    /**
     * 当用户评论了别人的帖子或者回复了别人的评论，被评论者或是被回复者会收到通知，
     * · 在评论或者是回复提交时，回将评论者和被评论者的id封装到实体类中，实体类中有一个status属性，0表示未读，1表示已读，插入数据库,Type表示消息类型
     * @param user
     * @param receiver
     * @param type
     * @param question
     */
    private void createNotify(User user,Long receiver,int type,Question question){
        if(receiver==user.getId()){
            return ;
        }
        Notification notification = new Notification();
        notification.setOuterTitle(question.getTitle());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifierName(user.getName());
        notification.setNotifier(user.getId());
        notification.setOuterid(question.getId());
        notification.setType(type);
        notification.setReceiver(receiver);
        notification.setStatus(0);
        notificationMapper.insert(notification);
    }

    /**
     * 获取一级评论或者二级评论
     * 根据传入的type决定
     * @param id
     * @return
     */
    public List<CommentDTO> listByTargetId(Long id,Integer type) {
        return commentMapper.listByQuestionId(id,type);
    }
}
