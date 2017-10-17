package com.aioute.dao.impl;

import com.aioute.dao.RolePermissionDao;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.sft.db.SqlConnectionFactory;
import com.sft.model.Permission;
import com.sft.model.Role;
import com.sft.model.bean.PermissionBean;
import com.sft.model.bean.RoleBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RolePermissionDaoImpl implements RolePermissionDao {

    @Resource
    private SqlConnectionFactory sqlConnectionFactory;

    public boolean addRole(Role role) {
        Connection con = null;
        PreparedStatement ps = null;
        StringBuffer sb = new StringBuffer();
        sb.append("insert into role (id,name,del_flag,remarks,create_by,create_date) values (?,?,?,?,?,?)");
        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, role.getId());
            ps.setString(2, role.getName());
            ps.setInt(3, role.getDel_flag());
            ps.setString(4, role.getRemarks());
            ps.setString(5, role.getCreate_by());
            ps.setString(6, role.getCreate_date());
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

    public Role getRole(String roleId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Role role = new Role();

        StringBuffer sb = new StringBuffer();
        sb.append("select * from role whre id = ?");

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, roleId);
            rs = ps.executeQuery();
            while (rs.next()) {
                role.setDel_flag(rs.getInt("del_flag"));
                role.setName(rs.getString("name"));
                role.setId(rs.getString("id"));
                role.setCreate_by(rs.getString("create_by"));
                role.setCreate_date(rs.getString("create_date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, null);
        }
        return role;
    }

    public boolean updateRole(Role role) {
        Connection con = null;
        PreparedStatement ps = null;
        StringBuffer sb = new StringBuffer();
        sb.append("update role set ");
        if (role.getDel_flag() != 0) {
            sb.append(" del_flag = 1");
        } else {
            sb.append(" del_flag = 0");
        }
        if (role.getName() != null) {
            sb.append(", name = '").append(role.getName()).append("'");
        }
        if (role.getRemarks() != null) {
            sb.append(", remarks = '").append(role.getRemarks()).append("'");
        }
        sb.append(" where id = ?");

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, role.getId());
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

    public boolean addUserRole(String roleId, String userId, String create_by, String create_date) {
        Connection con = null;
        PreparedStatement ps = null;
        StringBuffer sb = new StringBuffer();
        sb.append("insert into user_role (role_id,user_id,create_by,create_date) values (?,?,?,?)");

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, roleId);
            ps.setString(2, userId);
            ps.setString(3, create_by);
            ps.setString(4, create_date);
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

    public List<RoleBean> getRoles(Map<String, String> whereMap, int page, int pageSize) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<RoleBean> rolesList = new ArrayList<RoleBean>();
        StringBuffer sb = new StringBuffer();

        sb.append("select r.*,u.name as create_name from role r,sys_user u where u.id = r.create_by");
        if (whereMap != null) {
            String del_flag = whereMap.get("del_flag");
            if ("0".equals(del_flag) || "1".equals(del_flag)) {
                sb.append(" and r.del_flag = ").append(del_flag);
            }
            String name = whereMap.get("name");
            if (StringUtils.hasText(name)) {
                sb.append(" and r.name = '").append(name).append("'");
            }
        }
        sb.append(" order by r.create_date desc");
        if (page > 0 && pageSize > 0) {
            sb.append(" limit ");
            sb.append((page - 1) * pageSize).append(",").append(pageSize);
        }
        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                RoleBean role = new RoleBean();
                role.setId(rs.getString("id"));
                role.setName(rs.getString("name"));
                role.setRemarks(rs.getString("remarks"));
                role.setCreate_by(rs.getString("create_by"));
                role.setCreate_date(rs.getString("create_date"));
                role.setDel_flag(rs.getInt("del_flag"));
                role.setCreate_name(rs.getString("create_name"));
                rolesList.add(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return rolesList;
    }

    public int getRoleCount(Map<String, String> whereMap) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append("select count(1) as count from role");
        if (whereMap != null) {
            String del_flag = whereMap.get("del_flag");
            if ("0".equals(del_flag) || "1".equals(del_flag)) {
                sb.append(" where del_flag = ").append(del_flag);
            }
            String name = whereMap.get("name");
            if (StringUtils.hasText(name)) {
                if (sb.toString().contains("where")) {
                    sb.append(" and name = '").append(name).append("'");
                } else {
                    sb.append(" where name = '").append(name).append("'");
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

    public List<RoleBean> getRoles(String userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<RoleBean> rolesList = new ArrayList<RoleBean>();
        StringBuffer sb = new StringBuffer();

        sb.append("select r.*,u.name as create_name from role r,user_role ur,sys_user u where r.id = ur.role_id and ur.user_id = u.id and u.id = ?");
        sb.append(" and u.del_flag = 0 and r.del_flag = 0");
        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                RoleBean role = new RoleBean();
                role.setId(rs.getString("id"));
                role.setName(rs.getString("name"));
                role.setRemarks(rs.getString("remarks"));
                role.setCreate_by(rs.getString("create_by"));
                role.setCreate_date(rs.getString("create_date"));
                role.setDel_flag(rs.getInt("del_flag"));
                role.setCreate_name(rs.getString("create_name"));
                rolesList.add(role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return rolesList;
    }

    public boolean updateUserRoles(String userId, List<String> roleIdList, String time, String by) {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Statement stm = null;
        List<String> oriRoleIdList = new ArrayList<String>();

        try {
            con = sqlConnectionFactory.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement("select role_id from user_role where user_id = ?");
            rs = ps.executeQuery();
            while (rs.next()) {
                oriRoleIdList.add(rs.getString("role_id"));
            }

            List<String> deleteList = (List<String>) CollectionUtils.subtract(oriRoleIdList, roleIdList);
            List<String> addList = (List<String>) CollectionUtils.subtract(roleIdList, oriRoleIdList);

            int deleteLength, addLength = 0;
            if (deleteList == null) {
                deleteList = new ArrayList<String>();
            }
            if (addList == null) {
                addList = new ArrayList<String>();
            }
            if ((deleteLength = deleteList.size()) == 0 && (addLength = addList.size()) == 0) {
                return true;
            }

            stm = con.createStatement();
            StringBuffer sb = new StringBuffer();
            // 增加新的角色
            sb.append("insert into user_role (user_id,role_id,create_by,create_date) values ");
            if (addLength > 0) {
                for (int i = 0; i < addLength; i++) {
                    sb.append("(").append(userId).append(",");
                    sb.append(roleIdList.get(i)).append(",").append(by).append(",").append(time).append(")");
                    if (i < addLength - 1) {
                        sb.append(",");
                    }
                }
                stm.addBatch(sb.toString());
            }

            if (deleteLength > 0) {
                // 删除角色
                sb = new StringBuffer();
                sb.append("delete from user_role ur,sys_user u where u.parent_id_set like %").append(userId).append("%");
                sb.append(" and u.id = ur.user_id and ur.role_id in (");
                for (int i = 0; i < deleteLength; i++) {
                    sb.append(deleteList.get(i));
                    if (i < deleteLength - 1) {
                        sb.append(",");
                    }
                }
                stm.addBatch(sb.toString());
            }
            int[] result = stm.executeBatch();
            con.commit();
            con.setAutoCommit(true);
            int length = result.length;
            int l = length;
            for (int i = 0; i < length; i++) {
                l--;
            }
            return l == 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stm != null)
                    stm.close();
            } catch (Exception e) {

            }
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return false;
    }

    public boolean updateRolePermission(String roleId, List<String> permissionId) {
        Connection con = null;
        PreparedStatement ps = null;

        String sql = "delete from role_permission where role_id = '" + roleId + "'";
        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sql);
            int result = ps.executeUpdate();

            if (permissionId != null && permissionId.size() > 0) {
                StringBuffer sb = new StringBuffer();
                sb.append("insert into role_permission (role_id,permission_id) values ");

                int length = permissionId.size();
                for (int i = 0; i < length; i++) {
                    sb.append("('").append(roleId).append("','");
                    sb.append(permissionId.get(i)).append("')");
                    if (i < length - 1) {
                        sb.append(",");
                    }
                }
                ps = con.prepareStatement(sb.toString());
                result += ps.executeUpdate();
            }
            if (result >= 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, null);
        }
        return false;
    }

    public boolean addPermission(Permission permission) {
        if (permission == null) {
            return false;
        }
        Connection con = null;
        PreparedStatement ps = null;
        StringBuffer sb = new StringBuffer();
        sb.append("insert into permission (id,module_id,name,permission,create_by,create_date,remarks,del_flag,url,type) values (?,?,?,?,?,?,?,?,?,?)");
        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, permission.getId());
            ps.setString(2, permission.getModule_id());
            ps.setString(3, permission.getName());
            ps.setString(4, permission.getPermission());
            ps.setString(5, permission.getCreate_by());
            ps.setString(6, permission.getCreate_date());
            ps.setString(7, permission.getRemarks());
            ps.setInt(8, permission.getDel_flag() != 0 ? 1 : 0);
            ps.setString(9, permission.getUrl());
            ps.setString(10, permission.getType());
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

    public boolean updatePermission(Permission permission) {
        Connection con = null;
        PreparedStatement ps = null;
        StringBuffer sb = new StringBuffer();
        sb.append("update permission set");
        if (permission.getDel_flag() != 0) {
            sb.append(" del_flag = 1");
        } else {
            sb.append(" del_flag = 0");
        }
        if (permission.getModule_id() != null) {
            sb.append(", module_id = '").append(permission.getModule_id()).append("'");
        }
        if (permission.getName() != null) {
            sb.append(", name = '").append(permission.getName()).append("'");
        }
        if (permission.getRemarks() != null) {
            sb.append(", remarks = '").append(permission.getRemarks()).append("'");
        }
        if (permission.getType() != null) {
            sb.append(", type = '").append(permission.getType()).append("'");
        }
        if (permission.getUrl() != null) {
            sb.append(", url = '").append(permission.getUrl()).append("'");
        }
        if (permission.getUpdate_by() != null) {
            sb.append(", update_by = '").append(permission.getUpdate_by()).append("'");
        }
        if (permission.getUpdate_date() != null) {
            sb.append(", update_date = '").append(permission.getUpdate_date()).append("'");
        }
        if (permission.getPermission() != null) {
            sb.append(", permission = '").append(permission.getPermission()).append("'");
        }
        sb.append(" where id = ?");

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, permission.getId());
            int result = ps.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (MySQLIntegrityConstraintViolationException e) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, null);
        }
        return false;
    }

    public List<String> getPermissions(String userId) {
        StringBuffer sb = new StringBuffer();
        sb.append("select p.permission from role_permission rp,permission p where ");
        sb.append("rp.role_id = (select r.id from role r,user_role ur,plat_user u " +
                "where r.id = ur.role_id and ur.user_id = u.id and u.id = ? and u.del_flag = 0 and r.del_flag = 0)");
        sb.append(" and rp.permission_id = p.id and p.del_flag = 0");
        return getPermissions(sb.toString(), userId);
    }

    public List<PermissionBean> getPermissions(Map<String, String> whereMap, int page, int pageSize) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PermissionBean> permissionsList = new ArrayList<PermissionBean>();

        StringBuffer sb = new StringBuffer();
        sb.append("select p.*,s.name as module from permission p,sub_server s where s.id = p.module_id");
        if (whereMap != null) {
            String del_flag = whereMap.get("del_flag");
            if ("0".equals(del_flag) || "1".equals(del_flag)) {
                sb.append(" and p.del_flag = ").append(del_flag);
            }
            String name = whereMap.get("name");
            if (StringUtils.hasText(name)) {
                sb.append(" and p.name like %").append(name).append("%");
            }
            String url = whereMap.get("url");
            if (StringUtils.hasText(url)) {
                sb.append(" and p.url like %").append(url).append("%");
            }
            String type = whereMap.get("type");
            if (StringUtils.hasText(type)) {
                sb.append(" and p.type like %").append(type).append("%");
            }
            String permission = whereMap.get("permission");
            if (StringUtils.hasText(permission)) {
                sb.append(" and p.permission like %").append(permission).append("%");
            }
            String moduleId = whereMap.get("moduleId");
            if (StringUtils.hasText(moduleId)) {
                sb.append(" and p.module_id ='").append(moduleId).append("'");
            }
        }
        sb.append(" order by p.create_date desc");
        if (page > 0 && pageSize > 0) {
            sb.append(" limit ");
            sb.append((page - 1) * pageSize).append(",").append(pageSize);
        }

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                PermissionBean permission = new PermissionBean();
                permission.setRemarks(rs.getString("remarks"));
                permission.setModule_id(rs.getString("module_id"));
                permission.setName(rs.getString("name"));
                permission.setId(rs.getString("id"));
                permission.setPermission(rs.getString("permission"));
                permission.setModule(rs.getString("module"));
                permission.setCreate_date(rs.getString("create_date"));
                permission.setUrl(rs.getString("url"));
                permission.setType(rs.getString("type"));
                permission.setDel_flag(rs.getInt("del_flag"));
                permissionsList.add(permission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return permissionsList;
    }

    public List<PermissionBean> getPermissions() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PermissionBean> permissionsList = new ArrayList<PermissionBean>();

        StringBuffer sb = new StringBuffer();
        sb.append("select p.*,s.name as module from permission p,sub_server s where s.id = p.module_id");
        sb.append(" and p.del_flag = 0");

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                PermissionBean permission = new PermissionBean();
                permission.setRemarks(rs.getString("remarks"));
                permission.setModule_id(rs.getString("module_id"));
                permission.setName(rs.getString("name"));
                permission.setId(rs.getString("id"));
                permission.setPermission(rs.getString("permission"));
                permission.setModule(rs.getString("module"));
                permission.setCreate_date(rs.getString("create_date"));
                permission.setUrl(rs.getString("url"));
                permission.setType(rs.getString("type"));
                permission.setDel_flag(rs.getInt("del_flag"));
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

        sb.append("select count(1) as count from permission where 1 = 1");
        if (whereMap != null) {
            String del_flag = whereMap.get("del_flag");
            if ("0".equals(del_flag) || "1".equals(del_flag)) {
                sb.append(" and p.del_flag = ").append(del_flag);
            }
            String name = whereMap.get("name");
            if (StringUtils.hasText(name)) {
                sb.append(" and p.name like %").append(name).append("%");
            }
            String url = whereMap.get("url");
            if (StringUtils.hasText(url)) {
                sb.append(" and p.url like %").append(url).append("%");
            }
            String type = whereMap.get("type");
            if (StringUtils.hasText(type)) {
                sb.append(" and p.type like %").append(type).append("%");
            }
            String permission = whereMap.get("permission");
            if (StringUtils.hasText(permission)) {
                sb.append(" and p.permission like %").append(permission).append("%");
            }
            String moduleId = whereMap.get("moduleId");
            if (StringUtils.hasText(moduleId)) {
                sb.append(" and module_id ='").append(moduleId).append("'");
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

    public List<String> getRolePermissions(String roleId) {
        StringBuffer sb = new StringBuffer();
        sb.append("select p.permission from role_permission rp,permission p where rp.role_id = ?");
        sb.append(" and rp.permission_id = p.id and p.del_flag = 0");
        return getPermissions(sb.toString(), roleId);
    }

    public List<PermissionBean> getRolePermissionsList(String roleId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PermissionBean> permissionsList = new ArrayList<PermissionBean>();

        StringBuffer sb = new StringBuffer();
        sb.append("select p.*,sm.name as module from role_permission rp,permission p,sub_server sm where sm.id = p.module_id and rp.role_id = ?");
        sb.append(" and rp.permission_id = p.id and p.del_flag = 0");
        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, roleId);
            rs = ps.executeQuery();
            while (rs.next()) {
                PermissionBean permission = new PermissionBean();
                permission.setRemarks(rs.getString("remarks"));
                permission.setModule_id(rs.getString("module_id"));
                permission.setName(rs.getString("name"));
                permission.setId(rs.getString("id"));
                permission.setPermission(rs.getString("permission"));
                permission.setModule(rs.getString("module"));
                permission.setHas(true);
                permissionsList.add(permission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return permissionsList;
    }

    private List<String> getPermissions(String sql, String key) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> permissionsList = new ArrayList<String>();

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, key);
            rs = ps.executeQuery();
            while (rs.next()) {
                permissionsList.add(rs.getString("permission"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return permissionsList;
    }

    public List<PermissionBean> getUrlPermissions() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PermissionBean> permissionsList = new ArrayList<PermissionBean>();
        String sql = "select permission,url from permission where del_flag = 0";
        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PermissionBean permission = new PermissionBean();
                permission.setPermission(rs.getString("permission"));
                permission.setUrl(rs.getString("url"));
                permissionsList.add(permission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return null;
    }
}
