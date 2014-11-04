package com.wanzheng.wop.pub.util;

/*
 * 文件名：SessionListener.java
 * 描述： SessionListener.java
 * 修改人：zhangjianwei
 * 修改时间：2011-7-28
 * 修改内容：新增
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session监听类
 * @author     zhangjianwei
 * @version    V100R001 2011-7-28
 */
@SuppressWarnings("unchecked")
public class SessionListener implements HttpSessionListener,
        HttpSessionAttributeListener
{
    
    // 保存sessionID和username的映射
    private static HashMap hUserName = new HashMap();
    
    /** 以下是实现HttpSessionListener中的方法* */
    /* 创建session时，什么都不做 */
    public void sessionCreated(HttpSessionEvent se)
    {
    }
    
    /* session失效时，删除列表中用户信息 */
    public void sessionDestroyed(HttpSessionEvent se)
    {
        hUserName.remove(se.getSession().getId());
    }
    
    /** 以下是实现HttpSessionAttributeListener中的方法* */
    /* 调用session.setAttribute("username","****")时，添加用户信息到列表中 */
    public void attributeAdded(HttpSessionBindingEvent se)
    {
        if (se.getName().equals("username"))
        {
            hUserName.put(se.getSession().getId(), se.getValue());
        }
    }
    
    /* 调用session.removeAttribute("username","****")时，删除列表中用户信息 */
    public void attributeRemoved(HttpSessionBindingEvent se)
    {
        if (se.getName().equals("username"))
        {
            if (hUserName.containsValue(se.getValue()))
            {
                Iterator iter = hUserName.entrySet().iterator();
                while (iter.hasNext())
                {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    if (((String) val).equals(se.getValue()))
                    {
                        hUserName.remove(key);
                    }
                }
            }
        }
    }
    
    /* 调用更改"username"属性值时同时更改列表中用户信息 */
    public void attributeReplaced(HttpSessionBindingEvent se)
    {
        if (se.getName().equals("username"))
        {
            if (hUserName.containsValue(se.getValue()))
            {
                Iterator iter = hUserName.entrySet().iterator();
                while (iter.hasNext())
                {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    if (((String) val).equals(se.getValue()))
                    {
                        hUserName.remove(key);
                        hUserName.put(key, se.getValue());
                    }
                }
            }
        }
    }
    
    /* 返回用户列表 */
    public static HashMap getList()
    {
        return hUserName;
    }
}
