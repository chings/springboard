package springboard.shiro.realm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

public class JsonPermissionRealm extends AuthorizingRealm {

    @Autowired
    @Qualifier(value = "shiroPermissionJson")
    private JSONObject jsonObject;

    /*
     * 授权
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) principals.getPrimaryPrincipal();

        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        JSONObject currentUser = jsonObject.getJSONObject("users").getJSONObject(username);
        JSONArray jsonRoles = currentUser.getJSONArray("roles");

        for (int i = 0; i < jsonRoles.size(); i++) {
            String roleName = jsonRoles.getString(i);
            roles.add(roleName);

            JSONArray jsonPermissions = jsonObject.getJSONObject("roles").getJSONArray(roleName);
            for (int a = 0; a < jsonPermissions.size(); a++) {
                String p = jsonPermissions.getString(a);
                permissions.add(p);
            }
        }

        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    /*
     * 认证
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {

        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials()); // 得到密码

        JSONObject currentUser = jsonObject.getJSONObject("users").getJSONObject(username);

        if (currentUser == null) {
            throw new UnknownAccountException();// 没找到帐号
        }

        if (!currentUser.getString("password").equals(password)) {

            throw new IncorrectCredentialsException(); // 如果密码错误
        }

        return new SimpleAuthenticationInfo(username, password, getName());
    }

}
