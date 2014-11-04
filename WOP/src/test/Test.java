package test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.dragon.kernel.object.serialize.SerializeUtil;

/*
 * 文件名：Test.java
 * 描述： Test.java
 * 修改人：zhangshaojun
 * 修改时间：2011-8-6
 * 修改内容：新增
 */

/**
 * 添加描述
 * <p>
 * 
 * @author zhangshaojun
 */
public class Test
{
    public static void main(String[] args)
    {
        
        String url = "http://localhost:8080/WOP/devapp/";
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        
        // user
        url += "IUser";
        // xml +=
        // "<queryUserInfo><array length=\"1\"><string>shaojun2</string></array></queryUserInfo>";
        xml += "<getUserInfoVOByUserID><string>025sHayQ1005</string></getUserInfoVOByUserID>";
        // xml +=
        // "<deleteUserInfo><string>025MElvQ1001</string></deleteUserInfo>";
        // xml += "<queryUserDetail><string>shaojun</string></queryUserDetail>";
        // xml +=
        // "<addUserInfo><UserDetail><userName><![CDATA[tesa1]]></userName><pwd><![CDATA[123]]></pwd><type><![CDATA[0]]></type></UserDetail></addUserInfo>";
        // xml +=
        // "<addTeamMember><PersonInfoVO><teamID><![CDATA[025XB9wQ1001]]></teamID><memberID><![CDATA[025FT9wQ1001]]></memberID><status><![CDATA[0]]></status><identity><![CDATA[0]]></identity><type><![CDATA[0]]></type></PersonInfoVO></addTeamMember>";
        // xml +=
        // "<updateTeamMember><PersonInfoVO><teamID><![CDATA[025XB9wQ1001]]></teamID><memberID><![CDATA[025FT9wQ1001]]></memberID><status><![CDATA[1]]></status><identity><![CDATA[1]]></identity><type><![CDATA[0]]></type></PersonInfoVO><oprType>1</oprType></updateTeamMember>";
        
        // sortContent
        // url += "ISortContent";
        // xml +=
        // "<addTeamInfo><SortContentInfoVO><sortID><![CDATA[1]]></sortID><typeID><![CDATA[1]]></typeID><areaID><![CDATA[40]]></areaID><name><![CDATA[team4]]></name><description><![CDATA[11]]></description><userID><![CDATA[025RctvQ1001]]></userID><status><![CDATA[0]]></status></SortContentInfoVO></addTeamInfo>";
        // xml +=
        // "<deleteTeamInfo><string>0251H4wQ1005</string></deleteTeamInfo>";
        // xml +=
        // "<queryAllTeamsBySort><int>0</int><int>0</int><int>-1</int><int>0</int></queryAllTeamsBySort>";
        // xml +=
        // "<queryAllTeamsByType><int>0</int><int>0</int><int>1</int><int>1</int></queryAllTeamsByType>";
        // xml +=
        // "<queryTeamDetail><String>025jo8wQ1012</String></queryTeamDetail>";
        // xml +=
        // "<queryTeamsByParent><String>025XB9wQ1001</String><int>0</int><int>-1</int><int>0</int></queryTeamsByParent>";
        // xml +=
        // "<queryHotTeams><String></String><int>1</int><int>4</int></queryHotTeams>";
        // xml +=
        // "<queryAllTeams><String></String><String></String><String></String><String>33</String><int>1</int><int>-1</int><int>4</int></queryAllTeams>";
        System.out.println(xml);
        String response = doService(url, xml);
        System.out.println(response);
        
    }
    
    public static String doService(String url, String body)
    {
        
        StringBuilder res = new StringBuilder();
        DataOutputStream out = null;
        OutputStream output = null;
        DataInputStream in = null;
        InputStream input = null;
        BufferedReader br = null;
        
        try
        {
            // 1.建立请求，设置请求参数，以及编码要求
            URL reqURL = new URL(url);
            
            URLConnection conn = reqURL.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "UTF-8/object");
            
            // 2.发起请求，写入请求参数
            output = conn.getOutputStream();
            out = new DataOutputStream(output);
            out.writeUTF("queryUserInfo");
            out.writeInt(1);
            List<String> arr = new ArrayList<String>();
            arr.add(null);
            arr.add("王龙");
            arr.add("龙王");
            SerializeUtil sUtil = new SerializeUtil();
            sUtil.write(out, "0001");
            
            // 3.解析响应消息
            input = conn.getInputStream();
            in = new DataInputStream(input);
            Object result = sUtil.readFields(in);
            System.err.println(result.toString());
            
        }
        catch (Throwable e)
        {
            e.printStackTrace();
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
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        return res.toString();
    }
}
