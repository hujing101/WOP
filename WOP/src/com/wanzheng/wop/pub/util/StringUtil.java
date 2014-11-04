package com.wanzheng.wop.pub.util;

/*
 * 文件名：StringUtil.java
 * 描述： StringUtil.java
 * 修改人：zhangjianwei
 * 修改时间：2011-7-18
 * 修改内容：新增
 */

import java.util.List;

import com.wanzheng.wop.app.vo.BaseVO;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  brother80
 * @version  [版本号, 2014-10-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StringUtil
{
    /**
     * 
     * <自定义对象数组转字符串> <功能详细描述>
     * 
     * @param obj
     *            [一个字符类型数组]
     * 
     * @return String [返回类型说明]
     * @see [类、类#方法、类#成员]
     * @author lkf10893
     */
    public static String arrayObjToString(Object[] obj)
    {
        StringBuffer buffer = new StringBuffer();
        if (obj != null && obj.length > 0)
        {
            for (int i = 0; i < obj.length; i++)
            {
                buffer.append(null != obj[i] ? obj[i].toString() : "");
                buffer.append(";");
            }
            String strBuffer = getStrByBuffer(buffer);
            return strBuffer.substring(0, strBuffer.length() - 1);
        }
        return "";
    }
    
    /**
     * 
     * <int数组转字符串> <功能详细描述>
     * 
     * @param str
     *            [一个字符类型数组]
     * @return String [返回类型说明]
     * @see [类、类#方法、类#成员]
     * @author lkf10893
     */
    public static String arrayToString(int[] str)
    {
        StringBuffer buffer = new StringBuffer();
        if (str != null && str.length > 0)
        {
            for (int i = 0; i < str.length; i++)
            {
                buffer.append(str[i] + ",");
            }
            String strBuffer = getStrByBuffer(buffer);
            return strBuffer.substring(0, strBuffer.length() - 1);
        }
        return "";
    }
    
    /**
     * <数组转字符串>
     * 
     * @param str
     *            一个字符类型数组
     * @return String [返回类型说明]
     * @see [类、类#方法、类#成员]
     * @author lkf10893
     */
    public static String arrayToString(String[] str)
    {
        StringBuffer buffer = new StringBuffer();
        if (str != null && str.length > 0)
        {
            for (int i = 0; i < str.length; i++)
            {
                buffer.append(str[i] + ",");
            }
            String strBuffer = getStrByBuffer(buffer);
            return strBuffer.substring(0, strBuffer.length() - 1);
        }
        return "";
    }
    
    /**
     * 
     * <传入表达式中是否有一个为true>
     * 
     * @param bls
     *            需要判断的boolean数组
     * @return boolean [返回类型说明]
     */
    public static boolean assertOneTrueInAll(boolean... bls)
    {
        if (null == bls || bls.length == 0)
        {
            return false;
        }
        boolean allTrue = false;
        for (int i = 0; i < bls.length; i++)
        {
            if (bls[i])
            {
                allTrue = true;
                break;
            }
        }
        return allTrue;
    }
    
    /**
     * 验证列表是否为空
     * 
     * @param list
     * @return 如果为空返回true,如果不为空返回 false
     */
    public static boolean chechListHasValue(List<BaseVO> list)
    {
        if (null == list || list.isEmpty())
        {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * 根据用户信息反回类型，1用户名类型，2手机号类型，3邮箱类型。
     * 
     * @param account
     *            传入参数效验
     * @return
     */
    public static int checkAccountType(String account)
    {
        // 用户名
        if (account.matches("[a-z,A-Z,0-9]+"))
        {
            return 1;
        }
        // 手机号
        if (account.matches("\\d{11}"))
        {
            return 2;
        }
        // 邮箱
        if (account.matches("^\\w+@(\\w+\\.)+\\w{2,4}$"))
        {
            return 3;
        }
        return 1;
    }
    
    /**
     * 用于判断对象是否为空
     * 
     * @param o
     *            要判断是否为空的对象
     * @return 为空返回true,否则返回false
     */
    public static boolean checkObjEmpty(Object o)
    {
        if (null == o || o.equals(""))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * <校验分页参数起始编号和结束编号的范围> <功能详细描述>
     * 
     * @param startNumber
     *            startNumber
     * @param endNumber
     *            endNumber
     * @return boolean [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean checkStartNumAndEndNum(int startNumber, int endNumber)
    {
        if (startNumber <= 0 && startNumber != -1)
        {
            return false;
        }
        if (startNumber != -1 && endNumber < startNumber)
        {
            return false;
        }
        return true;
    }
    
    /**
     * 清除buffer
     * 
     * @param buffer
     *            参数说明
     * @see [类、类#方法、类#成员]
     */
    public static void clearStringBuffer(StringBuffer buffer)
    {
        if (buffer != null)
        {
            buffer.delete(0, buffer.length());
            buffer.capacity();
        }
    }
    
    /**
     * 字符串加解密
     * 
     * @param msgContent
     * @return String 加密或者机密后的String
     */
    public static String decodeMsg(String msgContent)
    {
        // 对消息内容进行加密|解密
        if (null != msgContent && msgContent.length() != 0)
        {
            return myCrypt(new StringBuffer(msgContent), 0x1E);
        }
        else
        {
            return "";
        }
        
    }
    
    /**
     * <组合成结果信息>pc返回值 <功能详细描述>
     * 
     * @param result
     *            result
     * @return String [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getResponseXML(String result)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<result>");
        sb.append("<resultCode>");
        sb.append(result);
        sb.append("</resultCode>");
        sb.append("</result>");
        return sb.toString();
    }
    
    /**
     * 清除并返回<功能详细描述>
     * 
     * @param buffer
     *            buffer
     * @return String [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getStrByBuffer(StringBuffer buffer)
    {
        String str = buffer.toString();
        clearStringBuffer(buffer);
        return str;
    }
    
    /**
     * <类型判断方法> <功能详细描述>
     * 
     * @param contentType
     *            内容类型
     * @param contentTypeStr
     *            内容类型字符串
     * @return boolean [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isContentType(String contentTypeStr,
            String contentType)
    {
        boolean isType = false;
        if (!isEmpty(contentTypeStr))
        {
            contentTypeStr = contentTypeStr.replaceAll(" ", "");
            String[] pictureType = contentTypeStr.split("\\|");
            for (int i = 0; pictureType != null && i < pictureType.length; i++)
            {
                if (pictureType[i] != null
                        && pictureType[i].equalsIgnoreCase(contentType))
                {
                    isType = true;
                }
            }
        }
        return isType;
    }
    
    /**
     * null或者空字符串都返回ture
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str)
    {
        return null == str || "".equals(str.trim());
    }
    
    public static boolean isEmpty(List list)
    {
        return null == list || list.size() == 0;
    }
    
    /**
     * 字符串加解密 (算法)
     * 
     * @param inp
     *            String
     * @param key
     *            移位符
     * @return 加密或者机密后的String
     */
    private static String myCrypt(StringBuffer inp, int key)
    {
        char k = (char) key;
        for (int x = 0; x < inp.length(); x++)
        {
            inp.setCharAt(x, (char) (inp.charAt(x) ^ k));
        }
        return inp.toString();
        
    }
    
    public static String subStr(String src, int len)
    {
        if (isEmpty(src) || src.length() <= len)
        {
            return src;
        }
        return src.substring(0, len) + "...";
    }
    
    public static boolean isEmpty(String... str)
    {
        for (String s : str)
        {
            if (isEmpty(s))
            {
                return true;
            }
        }
        return false;
    }
}
