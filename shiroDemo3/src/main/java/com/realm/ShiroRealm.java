package com.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yuanping on 2017/11/14.
 */
public class ShiroRealm extends AuthorizingRealm{
    //身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("[=============FirstRealm==========]");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();  //身份
        //登录传入的密码
        char[] password = token.getPassword(); //凭证
        System.out.println("principal:" + username);
        System.out.println(token.getCredentials());
        System.out.println("credentials:" + password);

        //调用数据库，查询是否存在username
        System.out.println("从数据库里获取用户信息:" + username);
        //如用户不存在，抛出异常
        if ("unknown".equals(username)) {
            throw new UnknownAccountException("用户不存在");
        }

        if ("monster".equals(username)) {
            throw new LockedAccountException("用户锁定");
        }

        String realmName = getName();
        //数据库查询的密码
        String dbPassword = null;
        if ("zhangsan".equals(username)) {
            dbPassword = "4e7bdb88640b376ac6646b8f1ecfb558";
        }
        if ("admin".equals(username)) {
            dbPassword = "0192023a7bbd73250516f069df18b500";
        }

        //盐值
        ByteSource byteSource = ByteSource.Util.bytes(username);

        //返回AuthenticationInfo对象，密码的比对是由shiro完成,加盐值
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, dbPassword, byteSource, realmName);
        return info;
    }

    //授权的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //从PrincipalCollection获取登录用户的信息
        Object principal = principals.getPrimaryPrincipal();

        //添加角色
        Set<String> roles = new HashSet<>();
        roles.add("user");
        if ("admin".equals(principal)) {
            roles.add("admin");
        }
        //返回授权SimpleAuthorizationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        Set<String> permissions = new HashSet<>();
        permissions.add("user:add");
        //添加用户权限
        info.addStringPermissions(permissions);

        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credentials = "123";
        Object salt = ByteSource.Util.bytes("admin");;
        int hashIterations = 1;
        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(simpleHash);
    }
}




















