package life.fallingu.commuity.commuity.service;

import life.fallingu.commuity.commuity.mapper.UserMapper;
import life.fallingu.commuity.commuity.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 更新用户或者是插入一个新用户
     * @param user
     */
    public void insertOrUpdate(User user) {
       User dbUser =  userMapper.findUserByAccountId(user.getAccountId());
       if(dbUser==null){
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insertUser(user);
       }else{
           dbUser.setAvatarUrl(user.getAvatarUrl());
           dbUser.setGmtModified(System.currentTimeMillis());
           dbUser.setToken(user.getToken());
           dbUser.setName(user.getName());
           userMapper.updateById(dbUser);
       }
    }
}
