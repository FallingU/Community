package life.fallingu.commuity.commuity.mapper;

import life.fallingu.commuity.commuity.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert(value = "insert into user(account_id,name,token,gmt_create,gmt_modified) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    int insertUser(User user);

    @Select(value = "select * from user where token = #{token}")
    User findUserByToken(@Param("token") String token);
}
