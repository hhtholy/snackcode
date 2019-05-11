package com.hhtholy.Realm;

import com.hhtholy.entity.User;
import com.hhtholy.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hht
 * @create 2019-04-19 9:28
 * shiro相关的认证和授权
 */
public class CustomeRealm extends AuthorizingRealm {

    //注入userService
    @Autowired
    private UserService userService;
    /**
     * 授权相关
     * 告诉shiro当前登陆用户有哪些权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        User userByName = userService.getUserByName(primaryPrincipal);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(userByName.getRole());
        return info;
    }
    /**
     * 认证(就是核对密码用户)
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取用户名  这个是用户输入的 比如表单
        String username = token.getPrincipal().toString();
        //进行数据库数据的查询
        //根据用户名进行数据的查询
        User user = userService.getUserByName(username);
        //获取用户的密码
        String password = user.getPassword();
        //获取盐
        String salt = user.getSalt();
        //shiro后续内部会进行认证
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, ByteSource.Util.bytes(salt), getName());
        return info;
    }

}
