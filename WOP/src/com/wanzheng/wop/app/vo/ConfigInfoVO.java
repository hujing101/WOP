/*
 * 文件名：ConfigInfoVO.java
 *  
 * 描述： ConfigInfoVO.java
 * 修改人：zsj
 * 修改时间：2011-8-17
 * 修改内容：新增
 */
package com.wanzheng.wop.app.vo;


/**
 * ConfigInfoVO
 * <p>
 * @author     zsj
 */
public class ConfigInfoVO extends BaseVO
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String key;
    private String value;
    private String desc;
    public String getKey()
    {
        return key;
    }
    public void setKey(String key)
    {
        this.key = key;
    }
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
    public String getDesc()
    {
        return desc;
    }
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
}
