package com.authenticator;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.mgt.SecurityManager;

/**
 * Created by Yuanping on 2017/11/12.
 */
public class AuthenticatorTest {
    public static void main(String[] args) {
        //构建我们的SecurityManager工厂，通过外部配置文件.ini的文件构建处理
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //通过工厂获得SecurityManager
        SecurityManager securityManager = factory.getInstance();
        //设置到应用环境中
        SecurityUtils.setSecurityManager(securityManager);

        //通过SecurityUtils获得Subject，shiro对外的api核心是subject，与代码直接交互的对象也是subject
        Subject subject = SecurityUtils.getSubject();

        //用户名和密码
        AuthenticationToken token = new UsernamePasswordToken("zhangsan", "123456");

        try {
            //用户登录
            subject.login(token);
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
        }

        boolean authenticated = subject.isAuthenticated();
        System.out.println("是否认证成功:" + authenticated);
    }
}

























