package springboard.example.model;

import java.util.List;

public interface AdminService {

    Role create(Role role);
    User create(User user);

    Role getRole(long id);
    User getUser(long id);
    User getUser(String username, String password);

    List<Role> findRoles(Role.Type type, String name, Integer pageNum, Integer pageSize);

    boolean update(Role role);
    boolean update(User user);

}
