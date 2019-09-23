package com.sinopec.springbootdemo.entity;


import java.util.Date;

public class Permission {
    private String autoId;
    private String uuid;
    private String name;
    private String url;
    private Date createTime;
    private int type;
    private String parentUuid;
    private String parentName;
    private int delFlag;

    public boolean isPermitted; // 用于在角色管理界面获取某角色是否被允许拥有该权限，经测试private无法正常工作

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Permission) {
            Permission p = (Permission) obj;
            return this.uuid.equals(p.getUuid());
        }
        return super.equals(obj);
    }

    public int getType() {
        return type;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

//    public boolean isPermitted() {
//        return isPermitted;
//    }
//
//    public void setPermitted(boolean permitted) {
//        this.isPermitted = permitted;
//    }
}