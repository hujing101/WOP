/*
 * 文 件 名:  HttpHandler.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  Administrator
 * 修改时间:  2014-11-2
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.wanzheng.wop.app.client.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.dragon.kernel.object.serialize.SerializeUtil;
import com.wanzheng.wop.app.contants.ErrorCode;
import com.wanzheng.wop.kernel.exc.WOPException;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  brother80
 * @version  [版本号, 2014-11-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HttpHandler
{
    
    /**
     * url
     */
    private static String url = "";
    
    public static SerializeUtil sUtil = new SerializeUtil();
    
    public static void init(int port, String ip)
    {
        url = "http://" + ip + ":" + port + "/WOP/devapp/";
    }
    
    public static Object invoke(String method, String serviceName,
            Object... params) throws WOPException
    {
        DataOutputStream out = null;
        OutputStream output = null;
        DataInputStream in = null;
        InputStream input = null;
        try
        {
            // 1.建立请求，设置请求参数，以及编码要求
            url += serviceName;
            URL reqURL = new URL(url);
            URLConnection conn = reqURL.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "UTF-8/object");
            
            // 2.发起请求，写入请求参数
            output = conn.getOutputStream();
            out = new DataOutputStream(output);
            out.writeUTF(method);
            out.writeInt(params.length);
            for (Object obj : params)
            {
                sUtil.write(out, obj);
            }
            
            // 3.解析响应消息
            input = conn.getInputStream();
            in = new DataInputStream(input);
            String resultCode = in.readUTF();
            if (ErrorCode.SUCCESS.equals(resultCode))
            {
                return sUtil.readFields(in);
            }
            throw new WOPException(resultCode);
        }
        catch (Throwable e)
        {
            if (e instanceof WOPException)
            {
                throw new WOPException(((WOPException) e).getCode());
            }
            throw new WOPException(ErrorCode.ERROR_INVOCATE_SERVICE);
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
