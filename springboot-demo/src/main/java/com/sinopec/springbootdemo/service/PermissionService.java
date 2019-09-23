package com.sinopec.springbootdemo.service;

import com.sinopec.springbootdemo.dao.PermissionDao;
import com.sinopec.springbootdemo.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    public List<Permission> getPermissionsByRoleUuid(String roleUuid) {
        return permissionDao.queryPermissionsByRoleUuid(roleUuid);
    }

    public Permission getPermissionByUuid(String pUuid) {
        return permissionDao.queryPermissionByUuid(pUuid);
    }

    public List<Permission> getAllPermissionPage(int limit, int page) {
        return permissionDao.queryAllPermissionPage(limit, page);
    }

    public int getPermissionCount() {
        return permissionDao.getPermissionCount();
    }

    public List<Permission> getAllPermission() {
        return permissionDao.queryAllPermission();
    }

    public void insertPermission(Permission p) {
        permissionDao.insertPermission(p);
    }

    public Permission getPermissionByName(String pName) {
        Permission p = permissionDao.queryPermissionByPName(pName);
        return p;
    }

    public void deleteRolePermissionByUuid(String roleUuid, String permissionUuid) {
        permissionDao.deleteRolePermissionByUuid(roleUuid, permissionUuid);
    }

    public void insertRolePermissionByUuid(String roleUuid, String permissionUuid) {
        try {
            String rpUuid = permissionDao.queryRolePermissionByUuid(roleUuid, permissionUuid);
            permissionDao.recoverExistRolePermissionByRPUuid(rpUuid);
        } catch (EmptyResultDataAccessException e) {
            permissionDao.insertRolePermissionByUuid(roleUuid, permissionUuid);
        }
    }
}
