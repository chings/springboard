package springboard.example.dao;

import org.apache.ibatis.annotations.*;
import springboard.example.model.Role;
import springboard.example.model.User;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users(id,username,password,last_logged_in_time,last_logged_in_addr) \n" +
            "VALUES(#{id},#{username},#{password},#{lastLoggedInTime},#{lastLoggedInAddr})")
    int insert(User user);

    @Select("SELECT u.*,r.type,r.name,r.created_time as createdTime FROM roles r, users u WHERE r.id=u.id AND u.id=#{id}")
    User selectById(@Param("id") long id);

    @Select("SELECT u.*,r.type,r.name,r.created_time as createdTime FROM roles r, users u WHERE r.id=u.id AND username=#{username}")
    User selectByUsername(@Param("username") String username);

    @Select("SELECT  u.*,r.type,r.name,r.created_time as createdTime FROM roles r, users u \n" +
            "WHERE r.id=u.id \n" +
            "  <if test='id != null'>AND u.id=#{id}</if>\n" +
            "  <if test='username != null'>AND username LIKE '%${username}%'}</if>\n" +
            "  <if test='name != null'>AND name LIKE '%${name}%'</if>\n" +
            "  <if test='createdTime0 != null'>AND createdTime &gt;= #{createdTime0}</if>\n" +
            "  <if test='createdTime1 != null'>AND createdTime &lt; #{createdTime1}</if>\n" +
            "ORDER BY id DESC")
    List<User> selectList(@Param("id") Long id, @Param("username") String username, @Param("name") String name, @Param("createdTime0") Date createdTime0, @Param("createdTime1") Date createdTime1);

    @Update("UPDATE users \n" +
            "  <set>\n" +
            "    <if test='password != null'>password=#{password},</if>\n" +
            "    <if test='lastLoggedInTime != null'>last_logged_in_time=#{lastLoggedInTime},</if>\n" +
            "    <if test='lastLoggedInAddr != null'>last_logged_in_addr=#{lastLoggedInAddr}</if>\n" +
            "  </set>\n" +
            "WHERE id=#{id}")
    int updateById(User user);

    @Delete("DELETE FROM users WHERE id=#{id}")
    int deleteById(@Param("id") Long id);

    @Insert("INSERT IGNORE INTO user_roles(user_id, role_id) VALUES(#{userId}, #{roleId})")
    int setRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Select("SELECT role_id FROM user_roles WHERE user_id=#{userId}")
    List<Long> findRoleIds(@Param("userId") Long userId);

    @Select("SELECT r.* FROM roles r, user_roles ur WHERE r.id=ur.role_id AND user_id=#{userId}")
    List<Role> findRoles(@Param("userId") Long userId);

    @Delete("DELETE FROM user_roles WHERE user_id=#{userId} AND role_id=#{roleId}")
    int unsetRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Delete("DELETE FROM user_roles WHERE user_id=#{userId}")
    int unsetAllRoles(@Param("userId") Long userId);

}
