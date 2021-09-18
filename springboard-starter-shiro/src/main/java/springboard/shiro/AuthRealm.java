package springboard.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import springboard.security.AuthService;

public class AuthRealm extends AuthorizingRealm {

    AuthService authService;

    public AuthRealm(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)getAvailablePrincipal(principals);
        Object user = authService.findSubject(username);
        if(user == null) throw new UnknownAccountException();

        SimpleAuthorizationInfo result = new SimpleAuthorizationInfo();
        result.setStringPermissions(authService.findPermissions(username));
        result.setRoles(authService.findRoles(username));

        return result;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = (String)token.getPrincipal();
        Object user = authService.findSubject(principal);
        if(user == null) throw new UnknownAccountException();

        String credential = new String((char[])token.getCredentials());
        user = authService.findSubject(principal, credential);
        if(user == null) throw new IncorrectCredentialsException();

        return new SimpleAuthenticationInfo(principal, credential, getName());
    }

}
