package springboard.example.model;

import org.springframework.data.domain.Page;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

public interface AdminService {

    Role createRole(Role role);
    User createUser(User user);

    Role getRole(long id);
    User getUser(long id);
    User getUser(String username);
    User getUser(String username, String password);

    Page<Role> findRoles(@Nullable Long id, @Nullable Role.Type type, @Nullable String name, @Nullable Date createdTime0, @Nullable Date createdTime1, int... pagination);
    Page<User> findUsers(@Nullable Long id, @Nullable User.Status status, @Nullable String username, @Nullable String name, @Nullable Date createdTime0, @Nullable Date createdTime1, int... pagination);

    List<Role> findRolesOfUser(long userId, Role.Type type);
    List<String> findRoleNamesOfUser(long userId, Role.Type type);

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
