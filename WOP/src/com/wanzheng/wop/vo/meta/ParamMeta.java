/*
 * 文件名：ParamMeta.java
 * 描述： ParamMeta.java
 * 修改人：zhangshaojun
 * 修改时间：2011-7-19
 * 修改内容：新增
 */
package com.wanzheng.wop.vo.meta;

import com.wanzheng.wop.app.vo.BaseVO;

/**
 * 组装xml请求体消息体的参数元数据
 * 
 * @author zhangshaojun
 * @version V100R001 2011-7-19
 */
public class ParamMeta extends BaseVO
{
    private static final long serialVersionUID = 1L;

    private String key;

    private Object value;

    public ParamMeta(String key, Object value)
    {
        this.key = key;
        this.value = value;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

}
