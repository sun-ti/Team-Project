package com.sinopec.springbootdemo.controller;

import com.sinopec.springbootdemo.entity.Role;
import com.sinopec.springbootdemo.myUtil.LayuiTableResultUtil;
import com.sinopec.springbootdemo.myUtil.RequiredUtil;
import com.sinopec.springbootdemo.service.PermissionService;
import com.sinopec.springbootdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String roleIndex() {
        return "role/index";
    }

    @ResponseBody
    @RequestMapping("roleList")
    public LayuiTableResultUtil<List> getRoleList(HttpServletRequest request) throws IOException {
        RequiredUtil requiredUtil = new RequiredUtil();
        if (!requiredUtil.Required(request.getParameter("limit").trim())) {
            return new LayuiTableResultUtil<List>("分页异常", null, 1, 10);
        }
        if (!requiredUtil.Required(request.getParameter("page").trim())) {
            return new LayuiTableResultUtil<List>("分页异常", null, 1, 10);
        }

        int limit = Integer.parseInt(request.getParameter("limit").trim());
        int page = Integer.parseInt(request.getParameter("page").trim());

        String roleName = request.getParameter("roleName");
        List<Role> roleList;
        int roleCount;

        if (roleName == null || roleName.isEmpty()) {
            roleList = roleService.getAllRolePage(limit, page);
            roleCount = roleService.getRoleCount();
        } else {
            roleList = new ArrayList<>();
            try {
                roleList.add(roleService.getRoleByName(roleName));
                roleCount = 1;
            } catch (EmptyResultDataAccessException e) {
                roleCount = 0;
            }
        }

        LayuiTableResultUtil<List> list = new LayuiTableResultUtil<List>("", roleList, 0, roleCount);

        if (roleList != null) {
            return list;
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("changeRolePermission")
    public void changeRolePermission(@RequestParam(value = "roleUuid") String roleUuid, @RequestParam(value = "permissionUuid") String permissionUuid, @RequestParam(value = "isAdd") boolean isAdd) {
        if (!isAdd) {
            permissionService.deleteRolePermissionByUuid(roleUuid, permissionUuid);
        } else {
            permissionService.insertRolePermissionByUuid(roleUuid, permissionUuid);
        }
    }
}
