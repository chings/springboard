package springboard.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import springboard.example.bean.Role;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select i.* from identity i, users_roles ur where i.id=ur.role_id and user_id=#{userId}")
    List<Role> findRoles(@Param("userId") Long userId);

    @Insert("insert ignore into users_roles(user_id, role_id) values(#{userId}, #{roleId})")
    int setRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Delete("delete from users_roles where user_id=#{userId} and role_id=#{roleId}")
    int unsetRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Delete("delete from users_roles where user_id=#{userId}")
    int unsetAllRoles(@Param("userId") Long userId);

}
