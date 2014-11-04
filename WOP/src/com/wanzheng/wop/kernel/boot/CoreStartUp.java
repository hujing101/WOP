package com.wanzheng.wop.kernel.boot;

import java.util.Properties;

/**
 * 系统初始化主控类 负责加载系统管理类: 1、基础管理类通过静态方法设置加载
 * 
 * 2、扩展管理类通过Spring配置加载
 */
public final class CoreStartUp
{
    /**
     * 系统是否启动的标志
     */
    private boolean isStarted = false;

    public boolean isStarted()
    {
        return isStarted;
    }


    /**
     * 启动网盘共享平台系统内核，初始化管理组件
     * 
     * @param props
     *            启动属性配置文件
     * @exception Exception
     *                Exception
     */
    public void init(Properties props) throws Exception
    {

        com.wanzheng.wop.kernel.mgmt.SJBMgmt.getInstance().init(props);
        
        // 系统启动结束
        isStarted = true;
    }
}