package com.aioute.dao.impl;

import com.aioute.dao.AppPermissionDao;
import com.aioute.model.AppPermission;
import com.sft.db.SqlConnectionFactory;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class AppPermissionDaoImpl implements AppPermissionDao {

    @Resource
    private SqlConnectionFactory sqlConnectionFactory;

    public boolean addAppPermission(AppPermission appPermission) {
        Connection con = null;
        PreparedStatement ps = null;
        StringBuffer sb = new StringBuffer();
        sb.append("insert into app_permission (id,remarks,type,url,is_user) values (?,?,?,?,?)");
        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, appPermission.getId());
            ps.setString(2, appPermission.getRemarks());
            ps.setString(3, appPermission.getType());
            ps.setString(4, appPermission.getUrl());
            ps.setInt(5, appPermission.getIs_user());
            int result = ps.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, null);
        }
        return false;
    }

    public boolean updateAppPermission(AppPermission appPermission) {
        Connection con = null;
        PreparedStatement ps = null;
        StringBuffer sb = new StringBuffer();
        sb.append("update app_permission set ");
        if (appPermission.getIs_user() != 0) {
            sb.append(" is_user = 1");
        } else {
            sb.append(" is_user = 0");
        }
        if (appPermission.getRemarks() != null) {
            sb.append(", remarks = '").append(appPermission.getRemarks()).append("'");
        }
        if (appPermission.getType() != null) {
            sb.append(", type = '").append(appPermission.getType()).append("'");
        }
        if (appPermission.getUrl() != null) {
            sb.append(", url = '").append(appPermission.getUrl()).append("'");
        }
        sb.append(" where id = ?");

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, appPermission.getId());
            int result = ps.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, null);
        }
        return false;
    }

    public List<AppPermission> getAppPermissions(Map<String, String> whereMap, int page, int pageSize) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AppPermission> permissionsList = new ArrayList<AppPermission>();

        StringBuffer sb = new StringBuffer();
        sb.append("select * from app_permission");
        if (whereMap != null) {
            String is_user = whereMap.get("is_user");
            if ("0".equals(is_user) || "1".equals(is_user)) {
                sb.append(" where is_user = ").append(is_user);
            }

            String url = whereMap.get("url");
            if (StringUtils.hasText(url)) {
                if (sb.toString().contains("where")) {
                    sb.append(" and url like %").append(url).append("%");
                } else {
                    sb.append(" where url like %").append(url).append("%");
                }
            }
            String type = whereMap.get("type");
            if (StringUtils.hasText(type)) {
                if (sb.toString().contains("where")) {
                    sb.append(" and type like %").append(type).append("%");
                } else {
                    sb.append(" where type like %").append(type).append("%");
                }
            }
        }

        if (page > 0 && pageSize > 0) {
            sb.append(" limit ");
            sb.append((page - 1) * pageSize).append(",").append(pageSize);
        }

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                AppPermission permission = new AppPermission();
                permission.setRemarks(rs.getString("remarks"));
                permission.setUrl(rs.getString("url"));
                permission.setType(rs.getString("type"));
                permission.setId(rs.getString("id"));
                permission.setIs_user(rs.getInt("is_user"));
                permissionsList.add(permission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return permissionsList;
    }

    public int getPermissionCount(Map<String, String> whereMap) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append("select count(1) as count from app_permission");
        if (whereMap != null) {
            String url = whereMap.get("url");
            if (StringUtils.hasText(url)) {
                if (sb.toString().contains("where")) {
                    sb.append(" and url like %").append(url).append("%");
                } else {
                    sb.append(" where url like %").append(url).append("%");
                }
            }

            String type = whereMap.get("type");
            if (StringUtils.hasText(type)) {
                if (sb.toString().contains("where")) {
                    sb.append(" and type like %").append(type).append("%");
                } else {
                    sb.append(" where type like %").append(type).append("%");
                }
            }
        }
        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return 0;
    }
}
