package com.dragon.kernel.endpoint.http.transform.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.dragon.kernel.constants.ErrorCode;
import com.dragon.kernel.constants.XHttpConstants;
import com.dragon.kernel.exc.XHttpException;

public class OBJObject extends OBJChain
{
    private OBJChain nextChain;
    
    public void addChain(OBJChain chain)
    {
        this.nextChain = chain;
    }
    
    @Override
    public Object doRead(DataInput in, Type paramType, Class<?> clz)
            throws XHttpException
    {
        if (isObject(clz, "java.lang"))
        {
            Object obj = null;
            try
            {
                obj = Class.forName(clz.getName()).newInstance();
                Field[] feilds = obj.getClass().getDeclaredFields();
                for (int i = 0; i < feilds.length; i++)
                {
                    // 子类的属性名
                    String property = feilds[i].getName();
                    if ("serialVersionUID".equals(property))
                    {
                        //序列号串，忽略
                        continue;
                    }
                    Object param = null;
                    // 读取对象是否为空的标识位，如果不为空，则使用解析链进行数据解析。
                    if (XHttpConstants.NOT_NULL_PARAM == in.readByte())
                    {
                        param = ObjectTool.getOBJChain().doRead(in,
                                feilds[i].getGenericType(),
                                feilds[i].getType());
                    }
                    // 通过set方法给数据赋值
                    Method method = obj.getClass().getDeclaredMethod("set"
                            + Character.toUpperCase(property.charAt(0))
                            + property.substring(1),
                            feilds[i].getType());
                    method.invoke(obj, param);
                }
                return obj;
            }
            catch (Exception e)
            {
                throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM, e);
            }
        }
        if (this.nextChain != null)
        {
            return this.nextChain.doRead(in, paramType, clz);
        }
        throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM);
    }
    
    @Override
    public void doWrite(DataOutput out, Object value) throws XHttpException
    {
        if (isObject(value.getClass(), "java.lang"))
        {
            Class clz = value.getClass();
            try
            {
                Field[] feilds = clz.getDeclaredFields();
                for (int i = 0; i < feilds.length; i++)
                {
                    // 子类的属性名
                    String property = feilds[i].getName();
                    if ("serialVersionUID".equals(property))
                    {
                        //序列号串，忽略
                        continue;
                    }
                    Method method;
                    String getName = null;
                    // 属性类型可能是boolean类型
                    if ("boolean".equalsIgnoreCase(feilds[i].getType()
                            .getName()))
                    {
                        getName = property;
                        if (!getName.startsWith("is") || getName.equals("is"))
                        {
                            getName = "is"
                                    + Character.toUpperCase(property.charAt(0))
                                    + property.substring(1);
                        }
                    }
                    else
                    {
                        // 通过get方法获取属性对应的值
                        getName = "get"
                                + Character.toUpperCase(property.charAt(0))
                                + property.substring(1);
                    }
                    method = clz.getDeclaredMethod(getName, new Class[0]);
                    Object filedValue = method.invoke(value, new Object[0]);
                    if (null == filedValue)
                    {
                        // 当对象为空时，写一个short型的0来标识null对象
                        out.writeByte(XHttpConstants.NULL_PARAM);
                        continue;
                        
                    }
                    // 当对象不为空时，写一个short型的1来标识非空对象
                    out.writeByte(XHttpConstants.NOT_NULL_PARAM);
                    // 对返回的属性值进行处理，分别针对BaseVO子类及数组对象
                    ObjectTool.getOBJChain().doWrite(out, filedValue);
                }
            }
            catch (Exception e)
            {
                throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM, e);
            }
        }
        else if (this.nextChain != null)
        {
            this.nextChain.doWrite(out, value);
        }
        else
        {
            throw new XHttpException(ErrorCode.ERROR_OBJECT_TRANSFORM);
        }
    }
    
    @Override
    public OBJChain getChain()
    {
        return this.nextChain;
    }
    
}
