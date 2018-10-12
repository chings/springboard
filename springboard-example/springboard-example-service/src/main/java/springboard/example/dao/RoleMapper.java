package springboard.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import springboard.example.model.Role;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Insert("INSERT IGNORE INTO role_permissions(role_id, permission) VALUES(#{roleId}, #{permission})")
    int setPermission(@Param("roleId") Long roleId, @Param("permission") String permission);

    @Select("SELECT permission FROM role_permissions WHERE role_id=#{roleId}")
    List<String> findPermissions(@Param("roleId") Long roleId);

    @Select("SELECT DISTINCT permission FROM role_permissions WHERE role_id IN #{roleIds}")
    List<String> findPermissions2(@Param("roleIds") List<Long> roleIds);

    @Delete("DELETE FROM role_permissions WHERE role_id=#{roleId} AND permission=#{permission}")
    int unsetPermission(@Param("roleId") Long roleId, @Param("permission") String permission);

    @Delete("DELETE FROM role_permissions WHERE role_id=#{roleId}")
    int unsetAllPermissions(@Param("roleId") Long roleId);

}
