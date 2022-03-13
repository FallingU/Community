package life.fallingu.commuity.commuity.mapper;

import java.util.List;

import life.fallingu.commuity.commuity.dto.CommentDTO;
import life.fallingu.commuity.commuity.pojo.Comment;
import life.fallingu.commuity.commuity.pojo.CommentExample;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CommentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbggenerated Sat Mar 12 13:16:06 CST 2022
     */
    int countByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbggenerated Sat Mar 12 13:16:06 CST 2022
     */
    int deleteByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbggenerated Sat Mar 12 13:16:06 CST 2022
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbggenerated Sat Mar 12 13:16:06 CST 2022
     */
    int insert(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbggenerated Sat Mar 12 13:16:06 CST 2022
     */
    int insertSelective(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbggenerated Sat Mar 12 13:16:06 CST 2022
     */
    List<Comment> selectByExample(CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbggenerated Sat Mar 12 13:16:06 CST 2022
     */
    Comment selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbggenerated Sat Mar 12 13:16:06 CST 2022
     */
    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbggenerated Sat Mar 12 13:16:06 CST 2022
     */
    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbggenerated Sat Mar 12 13:16:06 CST 2022
     */
    int updateByPrimaryKeySelective(Comment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comment
     *
     * @mbggenerated Sat Mar 12 13:16:06 CST 2022
     */
    int updateByPrimaryKey(Comment record);

    @Select(value = "select * from comment where parent_id =#{id} and type=#{type}")
    @Results(id = "questionDtoModelMap",value = {
            @Result(column = "id",property = "id"),
            @Result(column = "parent_id",property = "parentId"),
            @Result(column = "type",property = "type"),
            @Result(column = "commentator",property = "commentator"),
            @Result(column = "gmt_create",property = "gmtCreate"),
            @Result(column = "gmt_modified",property = "gmtModified"),
            @Result(column = "like_count",property = "likeCount"),
            @Result(column = "content",property = "content"),
            @Result(property = "user",
                    column = "commentator",
                    one=@One(select = "life.fallingu.commuity.commuity.mapper.UserMapper.selectByPrimaryKey"))
    })
    List<CommentDTO> listByQuestionId(@Param("id") Long id,@Param("type")Integer type);
}