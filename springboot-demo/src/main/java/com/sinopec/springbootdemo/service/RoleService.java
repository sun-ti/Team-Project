package com.sinopec.springbootdemo.service;

import com.sinopec.springbootdemo.dao.RoleDao;
import com.sinopec.springbootdemo.entity.Role;
import com.sinopec.springbootdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public Role getRoleByUserUuid(String userUuid) {
        return roleDao.queryRoleByUserUuid(userUuid);
    }

    public List<Role> getAllRole() {
        return roleDao.queryAllRole();
    }

    public void insertUserRole(User user, Role role) {
        roleDao.insertUserRole(user, role);
    }

    public Role getRoleByUuid(String roleUuid) {
        return roleDao.queryRoleByRoleUuid(roleUuid);
    }

    public List<Role> getAllRolePage(int limit, int page) {
        return roleDao.queryAllRoleListPage(limit, page);
    }

    public int getRoleCount() {
        return roleDao.getRoleCount();
    }

    public Role getRoleByName(String roleName) {
        return roleDao.queryRoleByName(roleName);
    }
}
