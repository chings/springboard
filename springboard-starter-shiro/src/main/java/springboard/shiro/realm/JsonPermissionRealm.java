package springboard.shiro.realm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class JsonPermissionRealm extends AuthorizingRealm {

    Logger log = LoggerFactory.getLogger(JsonPermissionRealm.class);

    private JSONObject jsonObject;

    public JsonPermissionRealm(){
        try {
            Resource resource = new ClassPathResource("permissons.json");
            InputStream inputStream = resource.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            String jsonString = sb.toString();
            jsonObject=JSONObject.parseObject(jsonString);
        } catch (IOException e) {
            log.info("读取基础permissions.json权限文件异常", e);
        }
    }

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
