package life.fallingu.commuity.commuity.controller;

import life.fallingu.commuity.commuity.dto.CommentDTO;
import life.fallingu.commuity.commuity.dto.ResultDTO;
import life.fallingu.commuity.commuity.enums.CommentTypeEnum;
import life.fallingu.commuity.commuity.exception.CustomizeErrorCode;
import life.fallingu.commuity.commuity.exception.CustomizeException;
import life.fallingu.commuity.commuity.pojo.Comment;
import life.fallingu.commuity.commuity.pojo.User;
import life.fallingu.commuity.commuity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

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
        User user = (User)session.getAttribute("user");
        if(user==null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        comment.setCommentator(user.getId());
        commentService.insert(comment,user);
        return ResultDTO.oKOf();
    }

    /**
     * 获取二级评论
     * @param id
     * @return
     */
    @RequestMapping(value = "/comment/{id}")
    @ResponseBody
    public ResultDTO<List<CommentDTO>> comments(@PathVariable("id")Long id){
        List<CommentDTO> list = commentService.listByTargetId(id, CommentTypeEnum.COMMENT.getType());
        return ResultDTO.oKOf(list);
    }
}
