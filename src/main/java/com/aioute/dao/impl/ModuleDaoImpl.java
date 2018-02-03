package com.aioute.dao.impl;

import com.aioute.dao.ModuleDao;
import com.sft.db.SqlConnectionFactory;
import com.sft.model.Module;
import com.sft.model.bean.ModuleBean;
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
public class ModuleDaoImpl implements ModuleDao {

    @Resource
    private SqlConnectionFactory sqlConnectionFactory;

    public List<ModuleBean> getAllModule(Map<String, String> whereMap, int page, int pageSize) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ModuleBean> moduleList = new ArrayList<ModuleBean>();

        StringBuffer sb = new StringBuffer();
        sb.append("select s.*,u.name as create_name from sub_server s,sys_user u where s.create_by = u.id");
        if (whereMap != null) {
            String del_flag = whereMap.get("del_flag");
            if ("1".equals(del_flag) || "0".equals(del_flag)) {
                sb.append(" and del_flag = ").append(del_flag);
            }
            String name = whereMap.get("name");
            if (StringUtils.hasText(name)) {
                sb.append(" and name = '").append(name).append("'");
            }
        }
        if (page > 0 && pageSize > 0) {
            sb.append(" order by create_date desc limit ");
            sb.append((page - 1) * pageSize).append(",").append(pageSize);
        }
        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());

            rs = ps.executeQuery();
            while (rs.next()) {
                ModuleBean module = new ModuleBean();
                module.setAddress(rs.getString("address"));
                module.setCreate_by(rs.getString("create_by"));
                module.setCreate_name(rs.getString("create_name"));
                module.setCreate_date(rs.getString("create_date"));
                module.setDel_flag(rs.getInt("del_flag"));
                module.setId(rs.getString("id"));
                module.setIsDirect(rs.getString("is_redict"));
                module.setName(rs.getString("name"));
                module.setRemarks(rs.getString("remarks"));
                moduleList.add(module);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlConnectionFactory.closeConnetion(con, ps, rs);
        }
        return moduleList;
    }

    public boolean addModule(Module module) {
        Connection con = null;
        PreparedStatement ps = null;
        StringBuffer sb = new StringBuffer();
        sb.append("insert into sub_server (id,name,del_flag,remarks,create_by,create_date,address) values (?,?,?,?,?,?,?)");
        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, module.getId());
            ps.setString(2, module.getName());
            ps.setInt(3, module.getDel_flag());
            ps.setString(4, module.getRemarks());
            ps.setString(5, module.getCreate_by());
            ps.setString(6, module.getCreate_date());
            ps.setString(7, module.getAddress());
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

    public boolean updateModule(Module module) {
        Connection con = null;
        PreparedStatement ps = null;
        StringBuffer sb = new StringBuffer();
        sb.append("update sub_server set ");
        if (module.getDel_flag() != 0) {
            sb.append(" del_flag = 1");
        } else {
            sb.append(" del_flag = 0");
        }
        if (module.getName() != null) {
            sb.append(", name = '").append(module.getName()).append("'");
        }
        if (module.getRemarks() != null) {
            sb.append(", remarks = '").append(module.getRemarks()).append("'");
        }
        if (module.getAddress() != null) {
            sb.append(", address = '").append(module.getAddress()).append("'");
        }
        if (module.getIsDirect() != null) {
            sb.append(", is_redict = '").append(module.getIsDirect()).append("'");
        }
        sb.append(" where id = ?");

        try {
            con = sqlConnectionFactory.getConnection();
            ps = con.prepareStatement(sb.toString());
            ps.setString(1, module.getId());
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

    public int getModuleCount(Map<String, String> whereMap) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        StringBuffer sb = new StringBuffer();

        sb.append("select count(1) as count from sub_server");
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
}
