package shiroDemo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Yuanping on 2017/11/13.
 */
public class Quickstart {
    private static final Logger logger = LoggerFactory.getLogger(Quickstart.class);

    public static void main(String[] args) {
        //获得SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro2.ini");
        //获得SecurityManager
        SecurityManager securityManager = factory.getInstance();

        //设securityManager
        SecurityUtils.setSecurityManager(securityManager);

        //获取当前Subject,调用SecurityUtils.getSubject
        Subject subject = SecurityUtils.getSubject();

        //测试使用Session
        Session session = subject.getSession();
        session.setAttribute("someKey", "aValue");

        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            logger.info("---> Retrieved the correct value!");
        }

        //测试当前的用户是否已经被认证，即是否已经登录
        //调动Subject 的isAuthenticated
        if (!subject.isAuthenticated()) {
            //把用户名和密码封装为UsernamePasswordToken
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            token.setRememberMe(true);

            try {
                //执行登录
                subject.login(token);
                //若没有指定的帐号，则shiro将会抛出UnknowAccountException异常
            } catch (UnknownAccountException e) {
                logger.info("---> There is no user with username of " + token.getPrincipal());
                return;
                //若帐号存在，但密码不匹配，则shiro会抛出IncorrectCredentialsException
            } catch (IncorrectCredentialsException ice) {
                logger.info("---> Password for account " + token.getPrincipal() + " was incorrect!");
                return;
                //用户被锁定的异常LockedAccountException
            } catch (LockedAccountException lae) {
                logger.info("The account for username " + token.getPrincipal() + " is locked.");
                //所有认证异常的父类
            } catch (AuthenticationException ae) {

            }

            logger.info("---> User [" + subject.getPrincipal() + " ] logged in successfully.");

            //测试是否某一个角色
            if (subject.hasRole("schwartz")) {
                logger.info("---> May the Schwartz be with you!");
            } else {
                logger.info("---> Hello ,mere mortal");
                return;
            }

            //测试用户是否具备某一个行为，调用Subjet的isPermitted
            if (subject.isPermitted("lightsaber:weild")) {
                logger.info("---> You may use a lightsaber ring . Use it wisely.");
            } else {
                logger.info(" Sorry, lightsaber rings are for schwartz masters only.");
            }

            //a (very powerful) Instance Level permission:
            // 测试用户是否具备某一个行为.
            if (subject.isPermitted("user:delete:zhangsan")) {
                logger.info("----> You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                        "Here are the keys - have fun!");
            } else {
                logger.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
            }

            //执行登出，调用Subject的Logout方法
            System.out.println("--->" + subject.isAuthenticated());
            subject.logout();
            System.out.println("--->" + subject.isAuthenticated());
            System.exit(0);
        }

    }
}



















