package com.sinopec.springbootdemo.controller;

import com.sinopec.springbootdemo.entity.Permission;
import com.sinopec.springbootdemo.myUtil.LayuiTableResultUtil;
import com.sinopec.springbootdemo.myUtil.RequiredUtil;
import com.sinopec.springbootdemo.service.PermissionService;
import com.sinopec.springbootdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/index")
    public String permissionIndex() {
        return "permission/index";
    }

    @ResponseBody
    @RequestMapping("/permissionList")
    public LayuiTableResultUtil<List> getPermissionList(HttpServletRequest request) throws IOException {
        RequiredUtil requiredUtil = new RequiredUtil();
        if (!requiredUtil.Required(request.getParameter("limit").trim())) {
            return new LayuiTableResultUtil<List>("分页异常", null, 1, 10);
        }
        if (!requiredUtil.Required(request.getParameter("page").trim())) {
            return new LayuiTableResultUtil<List>("分页异常", null, 1, 10);
        }

        int limit = Integer.parseInt(request.getParameter("limit").trim());
        int page = Integer.parseInt(request.getParameter("page").trim());

        String permissionName = request.getParameter("permissionName");
        List<Permission> permissionList;
        int permissionCount;

        // 判断是否为搜索行为（搜索框是否为空）
        if (permissionName == null || permissionName.isEmpty()) {
            permissionList = permissionService.getAllPermissionPage(limit, page);
            permissionCount = permissionService.getPermissionCount();
        } else {
            permissionList = new ArrayList<>();
            try {
                permissionList.add(permissionService.getPermissionByName(permissionName));
                permissionCount = 1;
            } catch (EmptyResultDataAccessException e) {
                permissionCount = 0;
            }
        }

        // 设定每个权限的父级权限
        for (Permission p : permissionList) {
            Permission parentP = permissionService.getPermissionByUuid(p.getParentUuid());
            if (parentP == null) {
                p.setParentName("");
            } else {
                p.setParentName(parentP.getName());
            }
        }

        // 检查是否需要获取角色与权限的关系（见templates/role/index）
        String flag = request.getParameter("toGetRolePermission");
        if (flag != null && flag.equals("true")) {
            String roleUuid = request.getParameter("roleUuid");
            for (Permission p : permissionList) {
                List<Permission> permittedList = permissionService.getPermissionsByRoleUuid(roleUuid);
                if (permittedList.contains(p)) {
                    p.isPermitted = true;
                }
            }
        }

        LayuiTableResultUtil<List> list = new LayuiTableResultUtil<>("", permissionList, 0, permissionCount);

        return list;
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String permissionAdd(Model model) {
        List<Permission> pList = new ArrayList<>();
        for (Permission p : permissionService.getAllPermission()) {
            pList.add(p);
        }
        model.addAttribute("permissionList", pList);
        model.addAttribute("permission", new Permission());
        return "permission/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String permissionAddInfo(@ModelAttribute Permission permission) {
        permissionService.insertPermission(permission);
        return "redirect:/permission/index";
    }
}
