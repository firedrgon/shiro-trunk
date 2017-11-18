package com.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;

import java.util.Date;

/**
 * Created by Yuanping on 2017/11/18.
 */
public class ShiroService {
    //需要admin权限
    @RequiresRoles({"admin"})
    public void testMethod() {
        System.out.println("testMethod====" + new Date());
        //获取Session对象
        Session session = SecurityUtils.getSubject().getSession();
        System.out.println(session.getAttribute("key"));
    }
}
