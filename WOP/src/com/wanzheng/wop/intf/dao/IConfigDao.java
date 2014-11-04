package com.wanzheng.wop.intf.dao;

import java.util.List;

import com.wanzheng.wop.app.vo.ConfigVO;
import com.wanzheng.wop.kernel.exc.WOPException;

public interface IConfigDao
{
    /**
     * 
     * 获取所有配置信息<key, value>
     * @return List [返回类型说明]
     * @throws WOPException [违例说明]
     * @see [类、类#方法、类#成员]
     * @author wanglong
     */
    public List<ConfigVO> getAllConfigInfo() throws WOPException;
    
    /**
     * 增加配置项
     * @param ConfigVO
     * @throws WOPException [参数说明]
     * @see [类、类#方法、类#成员]
     */
    public void addConfigInfoByKey(ConfigVO configVO) throws WOPException;
    
    /**
     * 更新配置项
     * @param configVO
     * @throws WOPException [参数说明]
     * @see [类、类#方法、类#成员]
     */
    public void updateConfigInfoByKey(ConfigVO configVO) throws WOPException;
}
