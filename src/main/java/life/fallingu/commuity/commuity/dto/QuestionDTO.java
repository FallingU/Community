package life.fallingu.commuity.commuity.dto;

import life.fallingu.commuity.commuity.pojo.User;
import lombok.Data;

/**
 * 用于给用户展示的文章对象
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
