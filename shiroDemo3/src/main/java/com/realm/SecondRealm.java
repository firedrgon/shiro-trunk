package com.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * Created by Yuanping on 2017/11/14.
 */
public class SecondRealm extends AuthenticatingRealm{
    //身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("[=============SecondRealm==========]");
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
            dbPassword = "5bf1276dfd87db1b274989ab90d3ad6cbe873b51";
        }
        if ("admin".equals(username)) {
            dbPassword = "f865b53623b121fd34ee5426c792e5c33af8c227";
        }

        //盐值
        ByteSource byteSource = ByteSource.Util.bytes(username);

        //返回AuthenticationInfo对象，密码的比对是由shiro完成,加盐值
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, dbPassword, byteSource, realmName);
        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "SHA1";
        Object credentials = "123";
        Object salt = ByteSource.Util.bytes("zhangsan");;
        int hashIterations = 1;
        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(simpleHash);
    }
}




















