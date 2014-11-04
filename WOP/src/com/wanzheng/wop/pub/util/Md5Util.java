package com.wanzheng.wop.pub.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * MD5加密
 * 
 * @author add
 * 
 */
public class Md5Util
{

    /*
     * 二行制转字符串
     */
    private static String byte2hex(final byte[] b)
    {
        String hs = "";
        String stmp = "";
        StringBuffer buf = new StringBuffer();
        for (int n = 0; n < b.length; n++)
        {
            stmp = Integer.toHexString(b[n] & 0XFF);

            if (stmp.length() == 1)
            {
                // hs = hs + "0" + stmp;
                buf.append("0" + stmp);
            }
            else
            {
                // hs = hs + stmp;
                buf.append(stmp);
            }
        }
        hs = buf.toString();

        return hs.toUpperCase(Locale.getDefault());
    }

    /**
     * 将字符串用md5算法编码.
     * 
     * @param str
     *            待转换字符串
     * @return 转换后字符串
     * @throws NoSuchAlgorithmException
     *             add
     */
    public static String encode(final String str)
            throws NoSuchAlgorithmException
    {
        final MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        return byte2hex(md.digest());
    }

    /**
     * 
     * 添加方法注释。
     * 
     * @param strIn
     *            add
     * @return String
     * @throws Exception
     *             add
     */
    public static String getHexMD5Str(String strIn) throws Exception
    {
        return getHexMD5Str(strIn.getBytes());
    }

    /**
     * 
     * 添加方法注释。
     * 
     * @param arrIn
     *            add
     * @return add
     * @throws Exception
     *             z
     */
    public static String getHexMD5Str(byte[] arrIn) throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] arrB = md.digest(arrIn);
        StringBuffer sb = new StringBuffer(32);
        String str = null;

        for (int i = 0; i < arrB.length; i++)
        {
            int intTmp = arrB[i];
            while (intTmp < 0)
            {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16)
            {
                sb.append('0');
            }
            sb.append(Integer.toString(intTmp, 16));
        }

        str = sb.toString();
        sb.delete(0, sb.length());
        sb.capacity();

        return str.toUpperCase(Locale.getDefault());
    }

    /**
     * MD5加密
     * 
     * @param s
     *            需要加密的字符串
     * @return 返回密文
     * @throws NoSuchAlgorithmException
     *             异常
     */
    public static final String getMd5(String s)
    {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        char str[];
        byte strTemp[] = s.getBytes();
        MessageDigest mdTemp;
        try
        {
            mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte md[] = mdTemp.digest();
            int j = md.length;
            str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(str).toUpperCase(Locale.getDefault());
        }
        catch (NoSuchAlgorithmException e1)
        {
            return "";
        }

    }

}