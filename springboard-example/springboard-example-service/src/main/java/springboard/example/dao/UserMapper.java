package springboard.example.dao;

import org.apache.ibatis.annotations.*;
import springboard.example.model.Role;
import springboard.example.model.User;
import springboard.mybatis.ValuedEnumTypeHandler;

import java.util.Date;
import java.util.List;

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
    User get(@Param("id") long id);

    @Select("SELECT u.*,r.type,r.name,r.created_time FROM roles r, users u WHERE r.id=u.id AND username=#{username}")
    @Results({
            @Result(column = "last_logged_in_time", property = "lastLoggedInTime"),
            @Result(column = "last_logged_in_addr", property = "lastLoggedInAddr"),
            @Result(column = "type", property = "type", javaType = Role.Type.class, typeHandler = ValuedEnumTypeHandler.class),
            @Result(column = "created_time", property = "createdTime")
    })
    User get2(@Param("username") String username);

    @Select("<script>" +
            "  SELECT  u.*,r.type,r.name,r.created_time FROM roles r, users u " +
            "  WHERE r.id=u.id " +
            "    <if test='id != null'>AND u.id=#{id}</if>" +
            "    <if test='username != null'>AND username LIKE '%${username}%'}</if>" +
            "    <if test='name != null'>AND name LIKE '%${name}%'</if>" +
            "    <if test='createdTime0 != null'>AND createdTime &gt;= #{createdTime0}</if>" +
            "    <if test='createdTime1 != null'>AND createdTime &lt; #{createdTime1}</if>" +
            "  ORDER BY id DESC" +
            "</script>")
    @Results({
            @Result(column = "last_logged_in_time", property = "lastLoggedInTime"),
            @Result(column = "last_logged_in_addr", property = "lastLoggedInAddr"),
            @Result(column = "type", property = "type", javaType = Role.Type.class, typeHandler = ValuedEnumTypeHandler.class),
            @Result(column = "created_time", property = "createdTime")
    })
    List<User> find(@Param("id") Long id, @Param("username") String username, @Param("name") String name, @Param("createdTime0") Date createdTime0, @Param("createdTime1") Date createdTime1);

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

    @Insert("INSERT IGNORE INTO user_roles(user_id, role_id) VALUES(#{userId}, #{roleId})")
    int setRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Select("SELECT role_id FROM user_roles WHERE user_id=#{userId}")
    List<Long> findRoleIds(@Param("userId") Long userId);

    @Select("SELECT r.* FROM roles r, user_roles ur WHERE r.id=ur.role_id AND user_id=#{userId}")
    @Results({
            @Result(column = "type", property = "type", javaType = Role.Type.class, typeHandler = ValuedEnumTypeHandler.class),
            @Result(column = "created_time", property = "createdTime")
    })
    List<Role> findRoles(@Param("userId") Long userId);

    @Delete("DELETE FROM user_roles WHERE user_id=#{userId} AND role_id=#{roleId}")
    int unsetRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Delete("DELETE FROM user_roles WHERE user_id=#{userId}")
    int unsetAllRoles(@Param("userId") Long userId);

}