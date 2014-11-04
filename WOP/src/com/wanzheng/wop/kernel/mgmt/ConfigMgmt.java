package com.wanzheng.wop.kernel.mgmt;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.wanzheng.wop.app.contants.ErrorCode;
import com.wanzheng.wop.app.vo.ConfigVO;
import com.wanzheng.wop.intf.service.IConfigService;
import com.wanzheng.wop.kernel.exc.WOPException;
import com.wanzheng.wop.pub.bean.SpringBeanName;
import com.wanzheng.wop.pub.util.StringUtil;

/**
 * 管理业务配置信息 提供获取业务级各种配置信息的获取接口
 * 
 * @author wkf7680
 * @version v1.0, 2008-9-8
 */
public class ConfigMgmt extends BaseMgmt
{
    private static ConfigMgmt configMgmt;
    
    private final static byte[] lock = new byte[0];
    
    public static ConfigMgmt getInstance()
    {
        if (null == configMgmt)
        {
            synchronized (lock)
            {
                configMgmt = new ConfigMgmt();
            }
        }
        return configMgmt;
    }
    
    // key对应数据库的key，value对应数据库的vlaue
    private Map<String, String> configmap = new Hashtable<String, String>();
    
    private ConfigMgmt()
    {
    }
    
    /**
     * 在配置表加入数据
     * 
     * @param key
     *            key
     * @param value
     *            [参数说明]
     * @see [类、类#方法、类#成员]
     */
    public void addConfig(String key, String value)
    {
        if (StringUtil.assertOneTrueInAll(StringUtil.isEmpty(key),
                StringUtil.isEmpty(value)))
        {
            return;
        }
        ConfigVO configVO = new ConfigVO();
        configVO.setAa_key(key);
        configVO.setAa_value(value);
        try
        {
            //this.getConfigService().addConfigInfoByKey(configVO);
            this.init(null);
        }
        catch (WOPException e)
        {
            // 记录日志
        }
    }
    
    public List<String> getConfigBySplit(String key, String splitValue)
    {
        if (null == key || "".equals(key.trim()))
        {
            return null;
        }
        
        String value = configmap.get(key);
        return Arrays.asList(value.split(splitValue));
    }
    
    /**
     * 获取节点service实例
     */
    private IConfigService getConfigService() throws WOPException
    {
        // 获取service
        IConfigService configService = (IConfigService) SJBMgmt.getInstance()
                .getBean(SpringBeanName.CONFIG_SERVICE);
        
        // bean不能为空
        if (null == configService)
        {
            // 记录日志
            throw new WOPException(ErrorCode.ERROR_INVOCATE_SERVICE,
                    "getConfigService fail", null);
        }
        
        return configService;
    }
    
    /**
     * 获取配置项值
     * 
     * @param key
     *            配置项的key，对应数据库的key字段
     * @return String
     */
    public String getConfigValue(String key)
    {
        if (null == key || "".equals(key.trim()))
        {
            return null;
        }
        
        return configmap.get(key);
    }
    
    /**
     * 获取配置项值
     * 
     * @author longhuan L20070 2011-01-21
     * @param key
     *            配置项的key，对应数据库的key字段
     * @param defaultValue
     *            如果配置项中取出的值为空，则返回这个默认值
     * @return 获取config表中key值对应的value
     */
    public String getConfigValue(String key, String defaultValue)
    {
        if (null == key || "".equals(key.trim()))
        {
            return null;
        }
        String value = configmap.get(key) == null ? defaultValue
                : configmap.get(key);
        return value;
    }
    
    /**
     * 系统启动时初始化方法，加载到内存中
     * 
     * @param props
     *            props
     * @throws SepException
     *             SepException
     * @author wKF7680 wangyangsheng 080908
     */
    public void init(Properties props) throws WOPException
    {
        if (null == configmap)
        {
            configmap = new Hashtable<String, String>();
        }
        else if (!configmap.isEmpty())
        {
            configmap.clear();
        }
        
        try
        {
            this.loadConfig();
        }
        catch (WOPException e)
        {
            // 记录日志
            System.exit(0);
            return;
        }
    }
    
    /**
     * 系统启动时加载数据库的配置项信息表
     * 
     * 
     * @throws WOPException
     */
    @SuppressWarnings("unchecked")
    private void loadConfig() throws WOPException
    {
        // 获取seivice实例
        //        IConfigService configService = this.getConfigService();
        
        //        List result = configService.getAllConfigInfo();
        //
        //        // if (null == result || result.isEmpty())
        //        // {
        //        // //记录日志
        //        // throw new AabarException(ErrorCode.PARAM_NULL_INVALID,
        //        // "loadConfig fail", null);
        //        // }
        //
        //        ConfigVO configvo = null;
        //
        //        for (int i = 0; null != result && i < result.size(); i++)
        //        {
        //            configvo = (ConfigVO) result.get(i);
        //            configmap.put(configvo.getAa_key(), configvo.getAa_value());
        //        }
    }
    
    /**
     * 更新配置表数据
     * 
     * @param key
     *            key
     * @param value
     *            [参数说明]
     * @see [类、类#方法、类#成员]
     */
    public void updateConfig(String key, String value)
    {
        if (StringUtil.assertOneTrueInAll(StringUtil.isEmpty(key),
                StringUtil.isEmpty(value)))
        {
            return;
        }
        ConfigVO configVO = new ConfigVO();
        configVO.setAa_key(key);
        configVO.setAa_value(value);
        try
        {
            //this.getConfigService().updateConfigInfoByKey(configVO);
            this.init(null);
        }
        catch (WOPException e)
        {
            // 记录日志
            
        }
    }
}