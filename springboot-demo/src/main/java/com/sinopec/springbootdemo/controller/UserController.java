package com.sinopec.springbootdemo.controller;

import com.sinopec.springbootdemo.dao.UserDao;
import com.sinopec.springbootdemo.entity.Role;
import com.sinopec.springbootdemo.entity.User;
import com.sinopec.springbootdemo.myUtil.LayuiTableResultUtil;
import com.sinopec.springbootdemo.myUtil.RequiredUtil;
import com.sinopec.springbootdemo.service.RoleService;
import com.sinopec.springbootdemo.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    public String userIndex() {
        return "user/index";
    }

    @ResponseBody
    @RequestMapping("/userList")
    public LayuiTableResultUtil<List> getUserList(HttpServletRequest request) throws IOException {
        RequiredUtil requiredUtil = new RequiredUtil();
        if (!requiredUtil.Required(request.getParameter("limit").trim())) {
            return new LayuiTableResultUtil<List>("分页异常", null, 1, 10);
        }
        if (!requiredUtil.Required(request.getParameter("page").trim())) {
            return new LayuiTableResultUtil<List>("分页异常", null, 1, 10);
        }

        int limit = Integer.parseInt(request.getParameter("limit").trim());
        int page = Integer.parseInt(request.getParameter("page").trim());
        String searchedUsername = request.getParameter("username");

        List<User> userList;
        int userCount;

        if (searchedUsername == null || searchedUsername.isEmpty()) {
            userList = userService.getAllUserPage(limit, page);
            userCount = userService.getUserCount();
        } else {
            userList = new ArrayList<>();
            try {
                userList.add(userService.getUserByUserName(searchedUsername));
                userCount = 1;
            } catch (EmptyResultDataAccessException e) {
                userCount = 0;
            }
        }

        for (User user : userList) {
            user.setRole(roleService.getRoleByUserUuid(user.getUuid()));
        }

        LayuiTableResultUtil<List> list = new LayuiTableResultUtil<List>("", userList, 0, userCount);

        if (userList != null) {
            return list;
        }
        return null;
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String userAdd(Model model) {
        List<Role> roleList = new ArrayList<>();
        for (Role role : roleService.getAllRole()) {
            roleList.add(role);
        }
        model.addAttribute("roleList", roleList);
        model.addAttribute("user", new User());
        return "user/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String userAddInfo(@ModelAttribute User user) {
        userService.insertUser(user);
        roleService.insertUserRole(userService.getUserByUserName(user.getUsername()), roleService.getRoleByUuid(user.getRoleUuid()));
        return "redirect:/user/index";
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void userDelete(@RequestParam(value = "deleteList[]") List<String> deleteUserUuid) {
        for (String uuid : deleteUserUuid) {
            userService.deleteUserByUuid(uuid);
        }
    }
}
