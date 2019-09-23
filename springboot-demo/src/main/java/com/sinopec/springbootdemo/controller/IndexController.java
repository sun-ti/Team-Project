package com.sinopec.springbootdemo.controller;

import com.sinopec.springbootdemo.entity.Permission;
import com.sinopec.springbootdemo.entity.User;
import com.sinopec.springbootdemo.myUtil.RedisUtil;
import com.sinopec.springbootdemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String root() {
//        System.out.println(username);
        return "login";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {
        return "error";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        try {
            subject.login(token);
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            return "redirect:/index";
        } catch (LockedAccountException lae) {
//            return "账户已锁定";
        } catch (ExcessiveAttemptsException eae) {
//            return "用户名或密码错误次数过多";
        } catch (AuthenticationException ae) {
            System.out.println("用户名或密码不正确");
//            return "用户名或密码不正确！";
        }
        token.clear();
        return "login";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        SecurityUtils.getSubject().logout();
        // TODO redis:redisServiceImpl.remove();

        return "redirect:/login";
    }

    @GetMapping("index")
    public String index(Model model, HttpServletRequest request) {
        User user = (User) redisUtil.get("currUser");
        // 显示用户对应角色的权限菜单
        List<Permission> menuList = new ArrayList<>();
        for (Permission p : user.getRole().getPermissionList()) {
            menuList.add(p);
        }
        model.addAttribute("menuList", menuList);
        return "index";
    }
}
