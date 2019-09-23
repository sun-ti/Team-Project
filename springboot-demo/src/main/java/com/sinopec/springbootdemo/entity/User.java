package com.sinopec.springbootdemo.entity;


import java.util.Date;

public class User {
    private String autoId;
    private String uuid;
    private String username;
    private String password;
    private Role role;
    private String roleUuid;
    private String roleName;
    private Date createTime;
    private Date refreshTime;
    private int refreshMark;
    private int delFlag;

    public String getRoleUuid() {
        return roleUuid;
    }

    public void setRoleUuid(String roleUuid) {
        this.roleUuid = roleUuid;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(Date refreshTime) {
        this.refreshTime = refreshTime;
    }

    public int getRefreshMark() {
        return refreshMark;
    }

    public void setRefreshMark(int refreshMark) {
        this.refreshMark = refreshMark;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
        roleName = role.getRoleName();
    }

    public String getRoleName() {
        return roleName;
    }
}
