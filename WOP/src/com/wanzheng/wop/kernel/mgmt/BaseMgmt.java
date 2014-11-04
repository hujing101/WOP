package com.wanzheng.wop.kernel.mgmt;

/*
 * 文 件 名:  BaseMgmt.java
 */

import java.util.Properties;

/**
 * 系统管理类的基类
 */
public abstract class BaseMgmt
{
    /**
     * 
     * init
     * 
     * @param props
     *            props
     * @throws Exception
     *             Exception
     */
    public abstract void init(Properties props) throws Exception;
}
