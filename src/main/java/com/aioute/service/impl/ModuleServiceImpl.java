package com.aioute.service.impl;

import com.aioute.dao.ModuleDao;
import com.aioute.service.ModuleService;
import com.sft.model.Module;
import com.sft.model.bean.ModuleBean;
import com.sft.util.DateUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class ModuleServiceImpl implements ModuleService {

    @Resource
    private ModuleDao serviceDao;

    public List<ModuleBean> getAllModule(Map<String, String> whereMap, int page, int pageSize) {
        return serviceDao.getAllModule(whereMap, page, pageSize);
    }

    public boolean addModule(Module module, String by) {
        if (module == null) {
            return false;
        }
        if (module.getDel_flag() != 0) {
            module.setDel_flag(1);
        }
        module.setId(UUID.randomUUID().toString());
        module.setCreate_date(DateUtil.getCurDate());
        module.setCreate_by(by);

        return serviceDao.addModule(module);
    }

    public boolean updateModule(Module module) {
        if (module == null) {
            return false;
        }
        if (module.getDel_flag() != 0) {
            module.setDel_flag(1);
        }
        return serviceDao.updateModule(module);
    }

    public int getModuleCount(Map<String, String> whereMap) {
        return serviceDao.getModuleCount(whereMap);
    }
}
