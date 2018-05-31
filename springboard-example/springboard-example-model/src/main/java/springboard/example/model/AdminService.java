package springboard.example.model;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.Date;
import java.util.List;

public interface AdminService {

    Role createRole(Role role);
    User createUser(User user);

    Role getRole(long id);
    User getUser(long id);
    User getUser(String username, String password);

    List<Role> findRoles(Long id, Role.Type type, String name, Date createdTime0, Date createdTime1, Integer... pagination);
    List<User> findUsers(Long id, String username, String name, Date createdTime0, Date createdTime1, Integer... pagination);

    List<Role> findRolesOfUser(long userId);

    List<String> findPermissionsOfRole(long roleId);
    List<String> findPermissionsOfUser(long userId);

    boolean updateRole(Role role);
    boolean updateUser(User user);

    boolean setUserRoles(long userId, long... roleIds);
    boolean unsetUserRoles(long userId, long... roleIds);

    boolean setRolePermissions(long roleId, String... permissions);
    boolean unsetRolePermissions(long roleId, String... permissions);

    boolean deleteRole(long id);
    boolean deleteUser(long id);

}
