package springboard.example.dao;

import org.apache.ibatis.annotations.*;
import springboard.example.model.Role;
import springboard.example.model.User;
import springboard.mybatis.ValuedEnumTypeHandler;

import java.util.Date;
import java.util.List;

@Mapper
public interface RoleMapper {

    @Insert("INSERT INTO roles(type,name,created_time) " +
            "VALUES(#{type,typeHandler=springboard.mybatis.ValuedEnumTypeHandler},#{name},#{createdTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(Role role);

    @Select("SELECT * FROM roles WHERE id=#{id}")
    @Results({
            @Result(column = "type", property = "type", javaType = Role.Type.class, typeHandler = ValuedEnumTypeHandler.class),
            @Result(column = "created_time", property = "createdTime")
    })
    Role get(long id);

    @Select("<script>" +
            "  SELECT * FROM roles " +
            "  <where>" +
            "    <if test='id != null'>id=#{id}</if>" +
            "    <if test='type != null'>AND type=#{type,typeHandler=springboard.mybatis.ValuedEnumTypeHandler}</if>" +
            "    <if test='name != null'>AND name like '%${name}%'</if>" +
            "    <if test='createdTime0 != null'>AND createdTime &gt;= #{createdTime0}</if>" +
            "    <if test='createdTime1 != null'>AND createdTime &lt; #{createdTime1}</if>" +
            "  </where>" +
            "  ORDER BY id DESC" +
            "</script>")
    @Results({
            @Result(column = "type", property = "type", javaType = Role.Type.class, typeHandler = ValuedEnumTypeHandler.class),
            @Result(column = "created_time", property = "createdTime")
    })
    List<Role> find(@Param("id") Long id, @Param("type") Role.Type type, @Param("name") String name, @Param("createdTime0") Date createdTime0, @Param("createdTime1") Date createdTime1);

    @Update("<script>" +
            "  UPDATE roles \n" +
            "    <set>\n" +
            "      <if test='name != null'>name=#{name}</if>\n" +
            "    </set>\n" +
            "  WHERE id=#{id}\n" +
            "</script>")
    int update(Role role);

    @Insert("INSERT IGNORE INTO role_permissions(role_id, permission) VALUES(#{roleId}, #{permission})")
    int setPermission(@Param("id") Long roleId, @Param("permission") String permission);

    @Select("SELECT permission FROM role_permissions WHERE role_id=#{roleId} AND permission=#{permission}")
    Long getPermission(@Param("id") Long roleId, @Param("permission") String permission);

    @Delete("DELETE FROM role_permissions WHERE role_id=#{roleId} AND permission=#{permission}")
    int unsetPermission(@Param("id") Long roleId, @Param("permission") String permission);

    @Delete("DELETE FROM role_permissions WHERE role_id=#{roleId}")
    int unsetAllPermissions(Long roleId);

}
