package life.fallingu.commuity.commuity.mapper;

import life.fallingu.commuity.commuity.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert(value = "insert into user(account_id,name,token,gmt_create,gmt_modified) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    int insertUser(User user);
}
