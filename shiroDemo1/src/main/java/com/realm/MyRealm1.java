package com.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * Created by Yuanping on 2017/11/12.
 */
public class MyRealm1 implements Realm {
    //返回一个唯一的Realm名字
    @Override
    public String getName() {
        return "myRealm1";
    }

    //判断此Realm是否支持Token
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        //仅支持UsernamePasswordToken类型的token
        return authenticationToken instanceof UsernamePasswordToken;
    }

    //根据token获取认证信息
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        if (!"zhangsan".equals(username)) {
            throw new UnknownAccountException(); //用户名错误
        }
        if (!"123".equals(password)) {
            throw new IncorrectCredentialsException(); //如果密码错误
        }
        //如果身份认证成功，则返回一个AuthenticationInfo的实现
        return new SimpleAuthenticationInfo(username, password, this.getName());
    }
}





















