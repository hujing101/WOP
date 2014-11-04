package com.wanzheng.wop.pub.util;

/*
 * 文件名：IDGenerator.java
 * 描述： IDGenerator.java
 * 修改人：zhangjianwei
 * 修改时间：2011-7-18
 * 修改内容：新增
 */
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.codehaus.xfire.addressing.RandomGUID;

import com.wanzheng.wop.kernel.exc.WOPException;

/**
 * TODO 添加类的一句话简单描述。
 * 
 * @author zhangjianwei
 * @version V100R001 2011-7-18
 */
public class IDGenerator
{
    /**
     * 调测日志记录器。
     */
    Logger log = Logger.getLogger(getClass());
    
    // 存储由10进制转62进制运算时的商和余数
    private static ArrayList<String> srcList = new ArrayList<String>();
    
    private static int count = 0;
    
    private static int msgCount = 0;
    
    /**
     * 生成userid,规则：(三位号首)＋(自1970后的秒数)（62进制6位）＋(三位流水号)
     * 
     * @param provCode
     *            省号
     * @param email
     *            邮箱
     * @return [12位的用户ID]
     * @throws WOPException
     *             1、错误代码;2、详细信息描述；3、原始异常
     */
    public static final synchronized String generateUserID()
    {
        
        String startNum = "027";
        srcList.clear();
        
        // 修改后的生成规则：三位区号 ＋自1970后的秒数)（62进制6位）＋(三位流水号)
        return startNum + getTimeStr() + getSeqNum();
    }
    
    /**
     * 生成msgid,规则：(userid)＋ 00 + (自1970后的秒数 十进制14位 )＋(四位流水号)
     * 
     * @param userid
     *            用户id
     * @return [32位的msgID]
     * @throws WOPException
     *             1、错误代码;2、详细信息描述；3、原始异常
     */
    public static final synchronized String getMessageID(String userid)
            throws WOPException
    {
        String startNum = "00";
        return userid + startNum + TimeUtil.generateRetryMissonID()
                + getMsgNum();
    }
    
    public static void main(String[] args) throws Exception
    {
        System.out.println(getMessageID("1234567890aa"));
        System.out.println(getMessageID("1234567890aa"));
        System.out.println(getMessageID("1234567890aa"));
    }
    
    /**
     * 1970年到现在的秒数的62进制字符串
     * 
     * @return String
     */
    private static final String getTimeStr()
    {
        final int rate = 1000;
        long secode = System.currentTimeMillis() / rate;// 转为秒
        
        convertJingZhi(secode);// 10进制转换到62进制
        String[] targetStr = convertToChar();// 62进制的数字用字符表示.0-9不变,
        // 10-35用A-Z表示，36-61用a-z表示
        StringBuffer buf = new StringBuffer();// 取后6位字符组成串
        final int timeLen = 5;
        int index = targetStr.length - 1;
        for (int i = index; i >= index - timeLen; i--)
        {
            buf.append(targetStr[i]);
        }
        srcList.clear();
        return buf.toString();
    }
    
    /**
     * 十进制转换成62进制
     * 
     * @param srcNum
     */
    private static final void convertJingZhi(long srcNum)
    {
        final int rate = 62;
        long cheng = srcNum / rate;
        long yuShu = srcNum % rate;
        if (cheng >= rate)
        {
            srcList.add(String.valueOf(yuShu));
            convertJingZhi(cheng);
        }
        srcList.add(String.valueOf(yuShu));
        srcList.add(String.valueOf(cheng));
    }
    
    /**
     * 生成三位数的序号，1-999
     * 
     * @return [序号]
     */
    private static final String getSeqNum()
    {
        String seq = "";
        final int base = 998;
        while (true)
        {
            count = count + 1;
            
            // 组装序列号
            seq = Integer.toString(count);
            if (seq.length() == 1)
            {
                seq = "00" + count;
            }
            if (seq.length() == 2)
            {
                seq = "0" + count;
            }
            
            // 计数器超过最大值后清零
            if (count > base)
            {
                count = 0;
                break;
            }
            else
            {
                break;
            }
        }
        return seq;
    }
    
    /**
     * 生成三位数的序号，1-9999
     * 
     * @return [序号]
     */
    private static final String getMsgNum()
    {
        String seq = "";
        final int base = 9998;
        while (true)
        {
            msgCount = msgCount + 1;
            
            // 组装序列号
            seq = Integer.toString(msgCount);
            if (seq.length() == 1)
            {
                seq = "000" + msgCount;
            }
            if (seq.length() == 2)
            {
                seq = "00" + msgCount;
            }
            if (seq.length() == 3)
            {
                seq = "0" + msgCount;
            }
            
            // 计数器超过最大值后清零
            if (msgCount > base)
            {
                msgCount = 0;
                break;
            }
            else
            {
                break;
            }
        }
        return seq;
    }
    
    /**
     * 把运算结果串换成用如下字符表示的串
     * 
     * 0-9 不变，10-35 用A-Z表示，36-61 用a-z表示
     */
    private static final String[] convertToChar()
    {
        String[] target = new String[srcList.size()];// 目标数根源
        
        int length = srcList.size();
        int targetIndex = 0;
        long ascii = 0;
        String c = "";
        
        final int rate = 55;
        final int rangeAZLimit = 35;
        final int rangeNumLimit = 10;
        final int rangeazLimit = 61;
        final int turnFlag = 65;
        for (int i = length - 1; i >= 0; i--)
        {
            String str = srcList.get(i);
            long numValue = Long.parseLong(str);
            if (rangeNumLimit <= numValue && numValue <= rangeAZLimit)
            {
                ascii = numValue + rate;
            }
            if (rangeAZLimit + 1 <= numValue && numValue <= rangeazLimit)
            {
                ascii = numValue + rangeazLimit;
            }
            if (0 <= numValue && numValue < rangeNumLimit)
            {
                ascii = numValue;
            }
            
            if (ascii >= turnFlag)
            {
                // ASCII码值大于等于65时，就由ASCII码转成字符(A-Z a-z)
                c = String.valueOf((char) ascii);
            }
            else
            {
                c = String.valueOf(ascii);
            }
            target[targetIndex] = c;
            targetIndex++;
        }
        return target;
    }
    
    // 生成内容id
    public static final synchronized String getConentID(String linkID)
    {
        RandomGUID randGUID = new RandomGUID(true);
        String fileID = randGUID.valueAfterMD5.substring(0, 5);
        // 5位物理文件id+12位userid+6位时间+4位序列值
        return fileID + linkID.substring(0, 12) + getTimeStr() + getMsgNum();
        
    }
}
