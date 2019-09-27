package springboard.example.web.component;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import springboard.example.model.AdminService;
import springboard.example.model.Role;
import springboard.example.model.User;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdminRealm extends AuthorizingRealm {

    @Reference
    AdminService adminService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)getAvailablePrincipal(principals);
        User user = adminService.getUser(username);
        if(user == null) throw new UnknownAccountException();

        SimpleAuthorizationInfo result = new SimpleAuthorizationInfo();
        Set<String> permissions = new HashSet<>();
        permissions.addAll(adminService.findPermissionsOfUser(user.getId()));
        result.setStringPermissions(permissions);

        Set<String> roles = new HashSet<>();
        roles.addAll(adminService.findRoleNamesOfUser(user.getId(), Role.Type.ROLE));
        result.setRoles(roles);

        return result;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        User user = adminService.getUser(username);
        if(user == null) throw new UnknownAccountException();

        String password = new String((char[])token.getCredentials());
        user = adminService.getUser(username, password);
        if(user == null) throw new IncorrectCredentialsException();

        return new SimpleAuthenticationInfo(username, password, getName());
    }

}