package com.wanzheng.wop.pub.util;

/*
 * 文 件 名:  InetUtil.java
 */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.server.ServerNotActiveException;

/**
 * 网络工具类
 * @author lidezhen
 * @version [版本号, 2009-8-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class InetUtil
{
    /**
     * 获取本地IP
     * 
     * @return String [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getInterAddrIP()
    {
        try
        {
            // 获取本地IP
            InetAddress address = InetAddress.getLocalHost();
            return address != null ? address.getHostAddress() : "";
        }
        catch (UnknownHostException e)
        {
            return "";
        }
    }

    /**
     * 获取rmi客户端IP
     * 
     * 
     * @return String [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getRmiClientIP()
    {
        try
        {
            return java.rmi.server.RemoteServer.getClientHost();
        }
        catch (ServerNotActiveException e)
        {
            return "";
        }
    }
}
