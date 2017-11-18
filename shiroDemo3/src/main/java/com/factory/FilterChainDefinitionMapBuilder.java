package com.factory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Yuanping on 2017/11/18.
 */
public class FilterChainDefinitionMapBuilder {
    public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("/login.jsp", "anon");
        map.put("/shiro/login", "anon");
        map.put("/shiro/logout", "logout");
        // /user.jsp,/admin.jsp需要认证
        map.put("/user.jsp", "authc,roles[user]");
        map.put("/admin.jsp", "authc,roles[admin]");
        //  /list.jsp RemenberMe就可以
        map.put("/list.jsp", "user");

        map.put("/**", "authc");
        return map;
    }
}
