package com.wanzheng.wop.pub.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.log4j.Logger;

import com.wanzheng.wop.kernel.exc.WOPException;


/**
 * 反射工具类
 */
public final class ReflectUtil
{
    private static Logger log = Logger.getLogger(ReflectUtil.class);

    private ReflectUtil()
    {

    }

    /**
     * 设置字段值
     * 
     * @param target
     *            待放射的类
     * 
     * @param fname
     *            类的方法名称后缀
     * @param ftype
     *            方法的参数类型
     * 
     * @param fvalue
     *            方法的参数值
     * 
     * @throws WOPException
     *             AabarException
     */
    @SuppressWarnings("unchecked")
    public static void setFieldValue(Object target, String fname, Class ftype,
            Object fvalue) throws WOPException
    {
        if (null == target
                || null == fname
                || "".equals(fname)
                || (fvalue != null && !ftype
                        .isAssignableFrom(fvalue.getClass())))
        {
            if (log.isDebugEnabled())
            {
                log.debug("ReflectUtil.setFieldValue error.");
            }
            return;
        }
        Class clazz = target.getClass();
        Class[] aa = {ftype};

        process(clazz, fname, aa, fvalue, target);
    }

    private static void process(Class<?> clazz, String fname, Class<?>[] aa,
            Object fvalue, Object target) throws WOPException
    {
        try
        {
            Method method = clazz.getDeclaredMethod("set"
                    + Character.toUpperCase(fname.charAt(0))
                    + fname.substring(1), aa);

            if (!Modifier.isPublic(method.getModifiers()))
            {
                method.setAccessible(true);
            }
            Object bb[] = {fvalue};

            method.invoke(target, bb);

        }
        catch (Exception me)
        {
            try
            {
                Field field = clazz.getDeclaredField(fname);
                if (!Modifier.isPublic(field.getModifiers()))
                {
                    field.setAccessible(true);
                }
                field.set(target, fvalue);
            }
            catch (Exception fe)
            {
                throw new WOPException(fname, "set FieldValue error", fe);
            }
        }
    }
}
