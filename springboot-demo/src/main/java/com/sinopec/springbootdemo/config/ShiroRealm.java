package com.sinopec.springbootdemo.config;

import com.sinopec.springbootdemo.entity.Permission;
import com.sinopec.springbootdemo.entity.Role;
import com.sinopec.springbootdemo.entity.User;
import com.sinopec.springbootdemo.myUtil.RedisUtil;
import com.sinopec.springbootdemo.service.PermissionService;
import com.sinopec.springbootdemo.service.RoleService;
import com.sinopec.springbootdemo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RedisUtil redisUtil;


    // 权限获取（getAuthorizationInfo 方法） 获取指定身份的权限，并返回相关信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // TODO 访问 @RequirePermission 注解的 url 时触发
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User userInfo = (User) principals.getPrimaryPrincipal();

        // 获取 User 的 Role 和 Permission 进行绑定
//        authorizationInfo.addRole(userInfo.getRole().getRoleName());
//        for (Permission permission : userInfo.getRole().getPermissionList()) {
//            authorizationInfo.addStringPermission(permission.getPermission());
//        }
        return authorizationInfo;
    }

    // 身份验证（getAuthenticationInfo 方法）验证账户和密码，并返回相关信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();

        // 从数据库查询 User 信息
        try {
            User user = userService.getUserByUserName(username);
            // 用户在登录页面输入的密码
            String inputPassword = new String((char[]) token.getCredentials());

            if (user.getPassword().equals(inputPassword)) {
                // 获取用户的角色信息以及对应的访问权限
                Role role = roleService.getRoleByUserUuid(user.getUuid());

                role.setPermissionList(permissionService.getPermissionsByRoleUuid(role.getRoleUuid()));
                user.setRole(role);
                redisUtil.set("currUser",user);
                return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            } else {
                throw new AuthenticationException();
            }
        } catch (EmptyResultDataAccessException e) { // 抛出查询结果为空的异常，说明用户名不存在
            throw new AuthenticationException();
        }
    }
}
