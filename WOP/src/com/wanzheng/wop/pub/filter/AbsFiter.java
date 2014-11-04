/*
 * 文件名：AbsFiter.java
 * 描述： AbsFiter.java
 * 修改人：lidezhen
 * 修改时间：2011-7-26
 * 修改内容：新增
 */
package com.wanzheng.wop.pub.filter;

import javax.servlet.http.HttpServletRequest;

import com.dragon.kernel.chain.Filter;
import com.dragon.kernel.endpoint.http.transform.MessageContext;
import com.dragon.kernel.exc.XHttpException;

/**
 * TODO 添加类的一句话简单描述。
 * 
 * @author lidezhen
 * @version V100R001 2011-7-26
 */
public abstract class AbsFiter extends Filter
{
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void doAfter(MessageContext arg0) throws XHttpException
    {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void doBefore() throws XHttpException
    {
        // TODO Auto-generated method stub
        
    }
    
    /**
     * 
     * {@inheritDoc}
     */
    public abstract void doBefore(HttpServletRequest request)
            throws XHttpException;
    
}
