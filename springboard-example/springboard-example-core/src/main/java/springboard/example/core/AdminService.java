package springboard.example.core;

import org.springframework.data.domain.Page;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

/* Keep POJOs and CRUD methods simple and ‘flat’, NO cascading or lazy-loading,
   in case of being adaptive in RPC situations. */
public interface AdminService {

    Role getRole(long id);
    Page<Role> listRoles(@Nullable Long id, @Nullable String name, @Nullable Date createdTime0, @Nullable Date createdTime1, int... pagination);
    Role createRole(Role role);
    boolean updateRole(long id, @Nullable String name);
    boolean purgeRole(long id);

    User getUser(long id);
    User getUser(String username);
    User getUser(String username, String password);
    Page<User> listUsers(@Nullable Long id, @Nullable Account.Status status, @Nullable String username, @Nullable String name, @Nullable Date createdTime0, @Nullable Date createdTime1, int... pagination);
    User createUser(User user);
    boolean updateUser(long id, @Nullable String name);
    boolean purgeUser(long id);

    Account findUserAccount(long userId);
    boolean updateUserAccount(long userId, @Nullable Account.Status status, @Nullable String password);
    boolean touchUserAccount(long userId, @Nullable Date lastLoggedInTime, @Nullable String lastLoggedInAddr);

    List<Role> findUserRoles(long userId);
    boolean setUserRoles(long userId, long... roleIds);
    boolean unsetUserRoles(long userId, long... roleIds);

    List<String> findPermissions(long identityId);
    List<String> findUserPermissions(long userId);
    boolean setPermissions(long identityId, String... permissions);
    boolean unsetPermissions(long identityId, String... permissions);

}
