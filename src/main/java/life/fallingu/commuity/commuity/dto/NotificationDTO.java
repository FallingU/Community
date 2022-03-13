package life.fallingu.commuity.commuity.dto;

import life.fallingu.commuity.commuity.pojo.NotificationType;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private NotificationType notificationType;
}
