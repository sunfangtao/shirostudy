package com.aioute.dao;

import com.sft.model.Module;
import com.sft.model.bean.ModuleBean;

import java.util.List;
import java.util.Map;

public interface ModuleDao {

    public List<ModuleBean> getAllModule(Map<String, String> whereMap, int page, int pageSize);

    public boolean addModule(Module module);

    public boolean updateModule(Module module);

    public int getModuleCount(Map<String, String> whereMap);
}
