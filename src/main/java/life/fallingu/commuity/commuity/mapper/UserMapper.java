package life.fallingu.commuity.commuity.mapper;

import life.fallingu.commuity.commuity.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert(value = "insert into user(account_id,name,token,gmt_create,gmt_modified,avatar_url) " +
            "values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    int insertUser(User user);

    @Select(value = "select * from user where token = #{token}")
    User findUserByToken(@Param("token") String token);

    @Select(value = "select * from user where id = #{id}")
    User findUserById(@Param("id") Integer id);

    @Select(value = "select * from user where account_id = #{accountId}")
    User findUserByAccountId(@Param("accountId") String accountId);

    @Update(value = "update user set avatar_url=#{avatarUrl},gmt_modified=#{gmtModified},token=#{token},name=#{name} where id = #{id}")
    void updateById(User user);
}
