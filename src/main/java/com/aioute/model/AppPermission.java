package com.aioute.model;

public class AppPermission {

    private String id;//'编号',
    private int is_user;//'是否需要登录',
    private String remarks;//'备注信息',
    private String url;//'链接地址',
    private String type;//'链接映射标识'

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIs_user() {
        return is_user;
    }

    public void setIs_user(int is_user) {
        this.is_user = is_user;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
