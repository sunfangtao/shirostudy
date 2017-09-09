package com.aioute.service;

import com.sft.model.Module;
import com.sft.model.bean.ModuleBean;

import java.util.List;
import java.util.Map;

public interface ModuleService {

    /**
     * 获取系统所有模块
     *
     * @return
     */
    public List<ModuleBean> getAllModule(Map<String, String> whereMap, int page, int pageSize);

    /**
     * 添加新模块
     *
     * @param module
     */
    public boolean addModule(Module module, String by);

    /**
     * 更新模块
     *
     * @param module
     */
    public boolean updateModule(Module module);

    /**
     * 获取总条数
     *
     * @param whereMap
     * @return
     */
    public int getModuleCount(Map<String, String> whereMap);
}
