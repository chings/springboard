package springboard.example.dao;

import org.apache.ibatis.annotations.*;
import springboard.example.model.Role;
import springboard.example.model.User;
import springboard.mybatis.ValuedEnumTypeHandler;

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
            "    <if test='type != null'>type=#{type,typeHandler=springboard.mybatis.ValuedEnumTypeHandler}</if>" +
            "    <if test='name != null'>AND name like '%${name}%'</if>" +
            "  </where>" +
            "  ORDER BY id DESC" +
            "</script>")
    @Results({
            @Result(column = "type", property = "type", javaType = Role.Type.class, typeHandler = ValuedEnumTypeHandler.class),
            @Result(column = "created_time", property = "createdTime")
    })
    List<Role> find(@Param("type") Role.Type type, @Param("name") String name);

    @Update("<script>" +
            "  UPDATE roles \n" +
            "    <set>\n" +
            "      <if test='name != null'>name=#{name}</if>\n" +
            "    </set>\n" +
            "  WHERE id=#{id}\n" +
            "</script>")
    int update(Role role);

}
