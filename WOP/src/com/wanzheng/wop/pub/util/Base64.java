package com.wanzheng.wop.pub.util;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author caohy
 */
public class Base64
{
    /**
     * 编码方法, 把一个字节数组编码为BASE64编码的字符串
     * 
     * @param arrB
     *            需要编码的字节数组
     * @return BASE64编码的字符串
     */
    public static String encode(byte[] arrB)
    {

        if (arrB == null)
        {
            return null;
        }
        return new BASE64Encoder().encode(arrB);
    }

    /**
     * 解码方法, 把一个BASE64编码的字符串解码为编码前的字节数组
     * 
     * 
     * 
     * @param in
     *            需要解码的BASE64编码的字符串
     * @return 解码后的字节数组, 若解码失败(该字符串不是BASE64编码)则返回null
     */
    public static byte[] decode(String in)
    {
        byte[] arrB = null;
        try
        {
            arrB = new BASE64Decoder().decodeBuffer(in);
        }
        catch (Exception ex)
        {
            arrB = null;
        }
        return arrB;
    }

    /**
     * 单元测试方法, 打印一个字符串在内存中的二进制值
     * 
     * 
     * 
     * @param in
     *            需要打印的字符串
     */
    public static void printByte(String in)
    {
        byte[] arrB = in.getBytes();
        for (int i = 0; i < arrB.length; i++)
        {
            System.out.print(Integer.toHexString(arrB[i]) + " ");
        }
    }

}