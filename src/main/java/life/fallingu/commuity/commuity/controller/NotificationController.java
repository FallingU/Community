package life.fallingu.commuity.commuity.controller;

import life.fallingu.commuity.commuity.pojo.Notification;
import life.fallingu.commuity.commuity.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    /**
     * 在点击了未读的通知后变为已读,并转向通知的来源帖子
     */
    @GetMapping("/notification/{id}")
    public String read(@PathVariable("id")Long id){
        Notification notification =  notificationService.read(id);

        return "redirect:/question/"+notification.getOuterid();
    }
}
