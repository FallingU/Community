package life.fallingu.commuity.commuity.mapper;

import java.util.List;

import life.fallingu.commuity.commuity.dto.NotificationDTO;
import life.fallingu.commuity.commuity.pojo.Notification;
import life.fallingu.commuity.commuity.pojo.NotificationExample;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NotificationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Sun Mar 13 11:06:09 CST 2022
     */
    int countByExample(NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Sun Mar 13 11:06:09 CST 2022
     */
    int deleteByExample(NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Sun Mar 13 11:06:09 CST 2022
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Sun Mar 13 11:06:09 CST 2022
     */
    int insert(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Sun Mar 13 11:06:09 CST 2022
     */
    int insertSelective(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Sun Mar 13 11:06:09 CST 2022
     */
    List<Notification> selectByExample(NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Sun Mar 13 11:06:09 CST 2022
     */
    Notification selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Sun Mar 13 11:06:09 CST 2022
     */
    int updateByExampleSelective(@Param("record") Notification record, @Param("example") NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Sun Mar 13 11:06:09 CST 2022
     */
    int updateByExample(@Param("record") Notification record, @Param("example") NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Sun Mar 13 11:06:09 CST 2022
     */
    int updateByPrimaryKeySelective(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated Sun Mar 13 11:06:09 CST 2022
     */
    int updateByPrimaryKey(Notification record);

    @Select(value = "select * from notification order by gmt_create desc")
    @Results(id = "notificationModelMap",value = {
            @Result(column = "id",property = "id"),
            @Result(column = "gmt_create",property = "gmtCreate"),
            @Result(column = "status",property = "status"),
            @Result(column = "notifier",property = "notifier"),
            @Result(column = "notifier_name",property = "notifierName"),
            @Result(column = "outer_title",property = "outerTitle"),
            @Result(column = "outerid",property = "outerid"),
            @Result(property = "notificationType",
                    column = "type",
                    one=@One(select = "life.fallingu.commuity.commuity.mapper.NotificationTypeMapper.selectByType"))
    })
    List<NotificationDTO> listByUserId(Long id);
}