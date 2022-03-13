package life.fallingu.commuity.commuity.dto;

import life.fallingu.commuity.commuity.pojo.User;
import lombok.Data;

/**
 * 用于给用户展示的文章对象
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Long commentCount;
    private Long viewCount;
    private Long likeCount;
    private String tag;
    private User user;
}
