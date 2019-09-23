package com.sinopec.springbootdemo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroRealm realm() {
        return new ShiroRealm();
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm());
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        Map<String, String> map = new LinkedHashMap<>();

        // 按顺序设置拦截链
        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        map.put("/logout", "logout");
        // 配置静态资源拦截器
        map.put("/static/**", "anon");
        map.put("/css/**", "anon");
        map.put("/font/**", "anon");
        map.put("/images.face/**", "anon");
        map.put("/lay.modules/**", "anon");
        // 登录页面拦截器
        map.put("/login", "anon");
        map.put("/user_add", "perms[user:add]");
        map.put("/**", "authc");

        // 设置登录结果页面url
        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/index");
//        bean.setUnauthorizedUrl("/error");

        bean.setFilterChainDefinitionMap(map);
        return bean;
    }

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
