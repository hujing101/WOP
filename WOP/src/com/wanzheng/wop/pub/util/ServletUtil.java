package com.wanzheng.wop.pub.util;

/*
 * 文件名：ServletUtil.java
 */

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet工具类
 */
public class ServletUtil
{

    /**
     * 提取服务名
     * 
     * @param request
     *            HttpServletRequest
     * 
     * @return String 服务名
     */
    public static String getService(HttpServletRequest request)
    {
        // pathInfo = "/appname"
        String pathInfo = request.getPathInfo();

        if (pathInfo == null)
        {
            return null;
        }

        String serviceName;

        if (pathInfo.startsWith("/"))
        {
            serviceName = pathInfo.substring(1);
        }
        else
        {
            serviceName = pathInfo;
        }

        return serviceName;
    }

}
