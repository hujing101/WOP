package com.wanzheng.wop.app.dao;

import java.sql.SQLException;
import java.util.List;

import com.wanzheng.wop.app.contants.ErrorCode;
import com.wanzheng.wop.app.vo.ConfigVO;
import com.wanzheng.wop.intf.dao.IConfigDao;
import com.wanzheng.wop.kernel.exc.WOPException;

public class ConfigDao extends BaseDao implements IConfigDao
{
    
    public void addConfigInfoByKey(ConfigVO configVO) throws WOPException
    {
        try
        {
            this.getSqlMapClient().insert("ConfigInfo.addConfigInfo", configVO);
        }
        catch (SQLException e)
        {
            //            log.log(LogNormalID.LOAD_CONFIG_FAILED, new String[] { "configDAO",
            //                    "insert config fail=" + configVO,
            //                    String.valueOf(e.getErrorCode()) }, e);
            
            throw new WOPException(ErrorCode.INSERT_EXCEPTION,
                    "ConfigDao.addConfigInfoByKey fail", e);
        }
        
    }
    
    @SuppressWarnings("unchecked")
    public List<ConfigVO> getAllConfigInfo() throws WOPException
    {
        List result = null;
        
        try
        {
            result = this.getSqlMapClient()
                    .queryForList("ConfigInfo.getAllConfigInfo");
        }
        catch (SQLException e)
        {
            //            log.log(LogNormalID.LOAD_CONFIG_FAILED, new String[] { "configDAO",
            //                    "iSAP Platform", String.valueOf(e.getErrorCode()) }, e);
            
            throw new WOPException(ErrorCode.QUERY_EXCEPTION,
                    "ConfigDao.getAllConfigInfo fail", e);
        }
        
        return result;
    }
    
    public void updateConfigInfoByKey(ConfigVO configVO) throws WOPException
    {
        try
        {
            this.getSqlMapClient().update("ConfigInfo.updateConfigInfoByKey",
                    configVO);
        }
        catch (SQLException e)
        {
            //            log.log(LogNormalID.LOAD_CONFIG_FAILED, new String[] { "configDAO",
            //                    "update config fail=" + configVO,
            //                    String.valueOf(e.getErrorCode()) }, e);
            //            
            throw new WOPException(ErrorCode.UPDATE_EXCEPTION,
                    "ConfigDao.updateConfigInfoByKey fail", e);
        }
        
    }
    
}
