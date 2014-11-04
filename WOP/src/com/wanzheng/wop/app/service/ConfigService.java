package com.wanzheng.wop.app.service;

import com.wanzheng.wop.intf.dao.IConfigDao;
import com.wanzheng.wop.intf.service.IConfigService;

public class ConfigService implements IConfigService
{
    private IConfigDao configDao;
    
    public IConfigDao getConfigDao()
    {
        return configDao;
    }
    
    public void setConfigDao(IConfigDao configDao)
    {
        this.configDao = configDao;
    }
    
}
