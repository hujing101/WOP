package com.wanzheng.wop.kernel.mgmt;

/*
 * 文件名：ResConfig.java
 * 描述： ResConfig.java
 * 修改人：lidezhen
 * 修改时间：2011-7-20
 * 修改内容：新增
 */
import java.io.FileInputStream;
import java.util.Properties;

public final class ResConfig
{
    private static final ResConfig resConfig = new ResConfig();
    
    private Properties prop;
    
    public void load(String file) throws Exception
    {
        prop = new Properties();
        prop.load(new FileInputStream(file));
    }
    
    public static final ResConfig getInstance()
    {
        return resConfig;
    }
    
    public String get(String key)
    {
        if (prop == null)
        {
            return null;
        }
        return prop.getProperty(key);
    }
    
    /**
     * 
     * 密码加密。
     * 
     * @param orgMd5
     * @return
     */
    public String pwdMd5(String orgMd5)
    {
        String keySecret = ""; //get(Constants.RES_KEY_SECRET);
        return com.wanzheng.wop.pub.util.Md5Util.getMd5(orgMd5 + keySecret);
    }
}
