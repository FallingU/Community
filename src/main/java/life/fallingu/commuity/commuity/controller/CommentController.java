package life.fallingu.commuity.commuity.controller;

import life.fallingu.commuity.commuity.mapper.CommentMapper;
import life.fallingu.commuity.commuity.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    /**
     * 获取前端传过来的评论的json数据，封装成实体后插入数据库并以json格式返回给前端
     * @param comment
     * @param session
     * @return
     */
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody Comment comment,
                       HttpSession session){
        comment.setCommentator(1);
        comment.setLikeCount(0L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        commentMapper.insert(comment);
        return comment;
    }
}
