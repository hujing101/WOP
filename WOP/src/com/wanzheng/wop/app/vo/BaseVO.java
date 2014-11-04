/*
 * 文件名：BaseVO.java
 * 描述： BaseVO.java
 * 修改人：lidezhen
 * 修改时间：2011-7-18
 * 修改内容：新增
 */
package com.wanzheng.wop.app.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 基础VO类
 * 
 * @author lidezhen
 */
public class BaseVO implements Serializable
{
    private static final long serialVersionUID = -3511025316554287293L;

    /**
     * 组装子类的xml字符串 <br>
     * 当子类不重写该方法时，直接使用父类方法通过反射组装子类的xml格式<br>
     * 特别处理BaseVO子类及数组，子类中其他复合类型对象(如Map等)未兼容
     * 
     * @author zhangshaojun
     * @return xml格式字符串
     */
    @SuppressWarnings("unchecked")
    public String asXml()
    {
        Class clz = this.getClass();
        String className = clz.getSimpleName();
        StringBuffer xml = new StringBuffer('<' + className + '>');

        Field[] feilds = clz.getDeclaredFields();
        for (int i = 0; i < feilds.length; i++)
        {
            // 子类的属性名
            String property = feilds[i].getName();

            try
            {
                // 通过get方法获取属性对应的值
                Method method = clz.getDeclaredMethod("get"
                        + Character.toUpperCase(property.charAt(0))
                        + property.substring(1), new Class[]{});
                Object filedValue = method.invoke(this, new Object[]{});

                // 对返回的属性值进行处理，分别针对BaseVO子类及数组对象
                parseObj(xml, property, filedValue);
            }
            catch (Exception e)
            {
            }
        }

        xml.append("</" + className + '>');
        return xml.toString();
    }

    /**
     * 解析对象，针对BaseVO类子类及数组递归处理 <br>
     * 对象中包括对象及数组时，分别以属性名为节点名
     * 
     * @param xml
     *            最终返回的xml字符串
     * @param property
     *            属性名
     * @param filedValue
     *            属性对应的值
     */
    private void parseObj(StringBuffer xml, String property, Object filedValue)
    {
        // System.out.println("调用VO" + filedValue.getClass().getSimpleName());
        // 如果属性也是对象,分BaseVO子类及数组处理
        // if (BaseVO.class.isAssignableFrom(filedValue.getClass()))
        if (null == filedValue)
        {
            return;
        }
        if (filedValue instanceof BaseVO)
        {
            // BaseVO子类直接调用asXml方法获取xml字符串形式
            xml.append(((BaseVO) filedValue).asXml());
        }
        else if (filedValue.getClass().isArray())
        {
            // 如果是数组
            Object[] obj = (Object[]) filedValue;

            xml.append("<" + property + "  length=\"" + obj.length + "\">");
            for (int i = 0; i < obj.length; i++)
            {
                // 针对数组元素，递归调用解析方法
                parseObj(xml, obj[i].getClass().getSimpleName(), obj[i]);
            }
            xml.append("</" + property + ">");
        }
        else
        {
            // 简单类型，直接组装
            xml.append("<" + property + ">");
            xml.append("<![CDATA[" + filedValue + "]]>");
            xml.append("</" + property + ">");
        }
    }

    @Override
    public String toString()
    {
        return asXml();
    }

}
