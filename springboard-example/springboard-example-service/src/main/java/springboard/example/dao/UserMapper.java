package springboard.example.dao;

import org.apache.ibatis.annotations.*;
import springboard.example.model.Role;
import springboard.example.model.User;
import springboard.mybatis.ValuedEnumTypeHandler;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users(id,username,password,last_logged_in_time,last_logged_in_addr) " +
            "VALUES(#{id},#{username},#{password},#{lastLoggedInTime},#{lastLoggedInAddr})")
    int create(User user);

    @Select("SELECT u.*,r.type,r.name,r.created_time FROM roles r, users u WHERE r.id=u.id AND u.id=#{id}")
    @Results({
            @Result(column = "last_logged_in_time", property = "lastLoggedInTime"),
            @Result(column = "last_logged_in_addr", property = "lastLoggedInAddr"),
            @Result(column = "type", property = "type", javaType = Role.Type.class, typeHandler = ValuedEnumTypeHandler.class),
            @Result(column = "created_time", property = "createdTime")
    })
    User get(long id);

    @Select("SELECT u.*,r.type,r.name,r.created_time FROM roles r, users u WHERE r.id=u.id AND username=#{username}")
    @Results({
            @Result(column = "last_logged_in_time", property = "lastLoggedInTime"),
            @Result(column = "last_logged_in_addr", property = "lastLoggedInAddr"),
            @Result(column = "type", property = "type", javaType = Role.Type.class, typeHandler = ValuedEnumTypeHandler.class),
            @Result(column = "created_time", property = "createdTime")
    })
    User getByUsername(String username);

    @Update("<script>" +
            "  UPDATE users \n" +
            "    <set>\n" +
            "      <if test='password != null'>password=#{password},</if>\n" +
            "      <if test='lastLoggedInTime != null'>last_logged_in_time=#{lastLoggedInTime},</if>\n" +
            "      <if test='lastLoggedInAddr != null'>last_logged_in_addr=#{lastLoggedInAddr}</if>\n" +
            "    </set>\n" +
            "  WHERE id=#{id}\n" +
            "</script>")
    int update(User user);

}
