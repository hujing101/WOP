package com.dragon.kernel.object.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dragon.kernel.constants.XHttpConstants;
import com.dragon.kernel.endpoint.http.transform.object.ObjectTool;
import com.dragon.kernel.endpoint.http.type.TypeFactory;
import com.dragon.kernel.endpoint.http.type.WrapType;

public class SerializeUtil implements Writable
{
    @Override
    public Object readFields(DataInput in) throws IOException
    {
        // 参数为空
        if (XHttpConstants.NULL_PARAM == in.readByte())
        {
            return null;
        }
        Object object = null;
        try
        {
            byte tc = in.readByte();
            String className;
            switch (tc)
            {
                case XHttpConstants.TC_ARRAY:
                    object = readArray(in);
                    break;
                case XHttpConstants.TC_LIST:
                    object = readList(in);
                    break;
                case XHttpConstants.TC_MAP:
                    object = readMap(in);
                    break;
                case XHttpConstants.TC_OBJECT:
                    className = in.readUTF();
                    Object obj = Class.forName(className).newInstance();
                    object = ObjectTool.getOBJChain().doRead(in,
                            obj.getClass(),
                            obj.getClass());
                    break;
                case XHttpConstants.TC_PRIMITIVE:
                    
                    className = in.readUTF();
                    WrapType type = TypeFactory.getInstance()
                            .getType(className);
                    object = type.doRead(in);
                    break;
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return object;
    }
    
    @Override
    public void write(DataOutput out, Object object) throws IOException
    {
        // 需要序列化的对象为空，写一个标识位出去即可
        if (null == object)
        {
            out.writeByte(XHttpConstants.NULL_PARAM);
            return;
        }
        try
        {
            out.writeByte(XHttpConstants.NOT_NULL_PARAM);
            if (object.getClass().isArray())
            {
                writeArray(out, object);
            }
            else if (isListOrMap(object.getClass(),
                    "java.util.List",
                    "java.util.ArrayList"))
            {
                writeList(out, object);
            }
            else if (isListOrMap(object.getClass(),
                    "java.util.Map",
                    "java.util.HashMap"))
            {
                writeMap(out, object);
                
            }
            else if (isBasicType(object.getClass(), "java.lang"))
            {
                out.writeByte(XHttpConstants.TC_PRIMITIVE);
                out.writeUTF(object.getClass().getName());
                ObjectTool.getOBJChain().doWrite(out, object);
            }
            else
            {
                out.writeByte(XHttpConstants.TC_OBJECT);
                out.writeUTF(object.getClass().getName());
                ObjectTool.getOBJChain().doWrite(out, object);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 序列化方法
     * 
     * @param object
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Object object) throws IOException
    {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        byte[] bytes = baos.toByteArray();
        return bytes;
        
    }
    
    /**
     * 反序列化
     * 
     * @param bytes
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object unserialize(byte[] bytes) throws IOException,
            ClassNotFoundException
    {
        ByteArrayInputStream bais = null;
        
        bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
        
    }
    
    protected boolean isListOrMap(Class<?> clz, String intfPkg, String implPkg)
    {
        return (clz.getName().equals(intfPkg))
                || (clz.getName().equals(implPkg));
    }
    
    protected boolean isBasicType(Class<?> clz, String pkgName)
    {
        return (clz.isPrimitive())
                || ((clz.getPackage() != null) && (clz.getPackage().getName().equals(pkgName)));
    }
    
    private void writeArray(DataOutput out, Object object) throws IOException
    {
        out.writeByte(XHttpConstants.TC_ARRAY);
        Object[] arr = (Object[]) object;
        int len = arr.length;
        out.writeInt(len);
        for (int i = 0; i < len; i++)
        {
            write(out, arr[i]);
        }
    }
    
    private Object readArray(DataInput in) throws IOException
    {
        Object[] arr = new Object[in.readInt()];
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = readFields(in);
        }
        return arr;
    }
    
    private void writeList(DataOutput out, Object object) throws IOException
    {
        out.writeByte(XHttpConstants.TC_LIST);
        List list = (List) object;
        int len = list.size();
        out.writeInt(len);
        for (int i = 0; i < len; i++)
        {
            write(out, list.get(i));
        }
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Object readList(DataInput in) throws IOException
    {
        List list = new ArrayList();
        int size = in.readInt();
        for (int i = 0; i < size; i++)
        {
            list.add(readFields(in));
        }
        return list;
    }
    
    private void writeMap(DataOutput out, Object object) throws IOException
    {
        out.writeByte(XHttpConstants.TC_MAP);
        Map map = (Map) object;
        int len = map.size();
        Entry en = null;
        for (Iterator<Entry> it = map.entrySet().iterator(); it.hasNext();)
        {
            en = it.next();
            write(out, en.getKey());
            write(out, en.getValue());
        }
        
    }
    
    private Object readMap(DataInput in) throws IOException
    {
        Map map = new HashMap();
        int size = in.readInt();
        for (int i = 0; i < size; i++)
        {
            map.put(readFields(in), readFields(in));
        }
        return map;
    }
}
