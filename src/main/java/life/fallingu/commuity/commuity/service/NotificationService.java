package life.fallingu.commuity.commuity.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.fallingu.commuity.commuity.dto.NotificationDTO;
import life.fallingu.commuity.commuity.exception.CustomizeErrorCode;
import life.fallingu.commuity.commuity.exception.CustomizeException;
import life.fallingu.commuity.commuity.mapper.NotificationMapper;
import life.fallingu.commuity.commuity.pojo.Notification;
import life.fallingu.commuity.commuity.pojo.NotificationExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;

    /**
     * 在评论时和回复时将封装好的通知实体放入数据库中
     * · 在访问首页时如果用户已登录，会查询数据库中的通知表查询多少个未读通知，存入session中，
     * · 用户在点击通知或者是在个人信息页面点击最新回复时，会发送请求到服务器端，服务器回查询接受者为当前用户的所有通知并通过倒序排序，如果status为0则表示未读，
     *
     * @param id
     * @param page
     * @param size
     * @return
     */
    public PageInfo<NotificationDTO> list(Long id, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<NotificationDTO> list =  notificationMapper.listByUserId(id);
        return new PageInfo<>(list,5);
    }

    public Notification read(Long id) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        Notification update = new Notification();
        update.setId(id);
        update.setStatus(1);
        notificationMapper.updateByPrimaryKeySelective(update);
        return notification;
    }

    public int unreadCount(Long id) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(0);
        return notificationMapper.countByExample(example);
    }
}
