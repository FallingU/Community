package life.fallingu.commuity.commuity.service;

import life.fallingu.commuity.commuity.mapper.UserMapper;
import life.fallingu.commuity.commuity.pojo.User;
import life.fallingu.commuity.commuity.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 更新用户或者是插入一个新用户
     * @param user
     */
    public void insertOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);
       if(users.size()!=0){
           user.setGmtCreate(System.currentTimeMillis());
           user.setGmtModified(user.getGmtCreate());
           userMapper.insertSelective(user);
       }else{
           User dbUser = users.get(0);
           User updateUser = new User();
           updateUser.setAvatarUrl(user.getAvatarUrl());
           updateUser.setGmtModified(System.currentTimeMillis());
           updateUser.setToken(user.getToken());
           updateUser.setName(user.getName());
           UserExample userExample = new UserExample();
           userExample.createCriteria().andIdEqualTo(dbUser.getId());
           userMapper.updateByExampleSelective(updateUser,userExample);
       }
    }
}
