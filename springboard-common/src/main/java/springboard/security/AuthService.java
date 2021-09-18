package springboard.security;

import java.util.Set;

public interface AuthService {

    Object findSubject(String principal);

    Object findSubject(String principal, String credential);

    Set<String> findPermissions(String principal);

    Set<String> findRoles(String principal);

}
