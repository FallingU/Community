package life.fallingu.commuity.commuity.mapper;

import life.fallingu.commuity.commuity.dto.QuestionDTO;
import life.fallingu.commuity.commuity.pojo.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert(value = "insert into question (title,description,gmt_create,gmt_modified,creator,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insert(Question question);

    @Select(value = "select * from question")
    @Results(id = "questionDtoModelMap",value = {
            @Result(column = "id",property = "id"),
            @Result(column = "title",property = "title"),
            @Result(column = "description",property = "description"),
            @Result(column = "gmt_create",property = "gmtCreate"),
            @Result(column = "gmt_modified",property = "gmtModified"),
            @Result(column = "creator",property = "creator"),
            @Result(column = "comment_count",property = "commentCount"),
            @Result(column = "view_count",property = "viewCount"),
            @Result(column = "like_count",property = "likeCount"),
            @Result(column = "tag",property = "tag"),
            @Result(property = "user",
                    column = "creator",
                    one=@One(select = "life.fallingu.commuity.commuity.mapper.UserMapper.findUserById"))
    })
    List<QuestionDTO> list();

    @Select(value = "select * from question where creator=#{id}")
    @ResultMap(value = "questionDtoModelMap")
    List<QuestionDTO> listByUserId(@Param("id") Integer id);
}
