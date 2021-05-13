package springboard.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import springboard.example.model.Identity;

import java.util.Collection;
import java.util.List;

@Mapper
public interface IdentityMapper extends BaseMapper<Identity> {

    @Select("select permission from identities_permissions where identity_id=#{identityId}")
    List<String> findPermissions(@Param("identityId") Long identityId);

    @Select("select distinct permission from identities_permissions where identity_id in (#{identityIds})")
    List<String> findAllPermissions(@Param("identityIds") Collection<Long> identityIds);

    @Insert("insert ignore into identities_permissions(identity_id, permission) values(#{identityId}, #{permission})")
    int setPermission(@Param("identityId") Long identityId, @Param("permission") String permission);

    @Delete("delete from identities_permissions where identity_id=#{identityId} and permission=#{permission}")
    int unsetPermission(@Param("identityId") Long identityId, @Param("permission") String permission);

    @Delete("delete from identities_permissions where identity_id=#{identityId}")
    int unsetAllPermissions(@Param("identityId") Long identityId);

}
