/*
 * 文件名：ConInfoMgmt.java
 * 描述： ConInfoMgmt.java
 * 修改人：zhangshaojun
 * 修改时间：2011-8-22
 * 修改内容：新增
 */
package com.wanzheng.wop.kernel.mgmt;

import java.util.Properties;

/**
 * 存储数据库常量信息<br>
 * 包括区域信息、分类信息、子类信息
 * 
 * @author zhangshaojun
 */
public class CommonInfoMgmt extends BaseMgmt
{
    
    private static CommonInfoMgmt instance;
    
    private final static byte[] lock = new byte[0];
    
    private CommonInfoMgmt()
    {
    }
    
    public static CommonInfoMgmt getInstance()
    {
        if (null == instance)
        {
            synchronized (lock)
            {
                instance = new CommonInfoMgmt();
            }
        }
        return instance;
    }
    
    @Override
    public void init(Properties props) throws Exception
    {
    }
    
}
