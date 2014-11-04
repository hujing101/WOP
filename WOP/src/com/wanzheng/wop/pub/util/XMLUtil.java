/*
 * 文件名：XMLUtil.java
 * 描述： XMLUtil.java
 * 修改人：zhangshaojun
 * 修改时间：2011-7-15
 * 修改内容：新增
 */
package com.wanzheng.wop.pub.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.wanzheng.wop.app.vo.BaseVO;
import com.wanzheng.wop.vo.meta.ParamMeta;


/**
 * @author zhangshaojun
 */
public class XMLUtil
{
    public final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    private static XMLUtil instance = null;

    public static XMLUtil getInstance()
    {
        if (null == instance)
        {
            return new XMLUtil();
        }
        return instance;
    }

    private XMLUtil()
    {

    }

    /**
     * 组装请求消息体
     * 
     * @param methodName
     *            接口名
     * @param map
     *            参数【key对应xml属性标签名，value对应属性值】
     * @return
     */
    public String genReqXml(String methodName, ParamMeta[] paras)
    {
        StringBuffer reqBode = new StringBuffer(XML_HEAD);
        reqBode.append("<" + methodName + ">");
        for (int i = 0; null != paras && i < paras.length; i++)
        {
            parseObj(paras[i].getValue(), paras[i].getKey(), reqBode);
        }

        reqBode.append("</" + methodName + ">");
        return reqBode.toString();
    }

    private void parseObj(Object obj, String key, StringBuffer reqBode)
    {

        if (null == obj)
        {
            return;
        }
        // 为vo类或数组
        if (isComplexObject(obj))
        {
            // 如果是vo类，则获取vo类对应的xml字符串
            if (obj instanceof BaseVO)
            {
                reqBode.append(((BaseVO) obj).asXml());
            }
            else
            {
                // 对象为数组
                Object[] paras = (Object[]) obj;

                // 组转数据头部
                reqBode.append("<array length=\"" + paras.length + "\">");

                // 遍历数据每个对象
                for (int i = 0; i < paras.length; i++)
                {
                    // 如果数组元素是vo类或数组，则递归调用本方法
                    if (isComplexObject(paras[i]))
                    {
                        // 如果元素也是数组且基本类型，则节点名直接使用数组元素类型名，如string
                        parseObj(paras[i], paras[i].getClass().getSimpleName(),
                                reqBode);
                    }
                    else
                    {
                        // 普通类型
                        reqBode.append("<id>" + paras[i] + "</id>");
                    }
                }
                reqBode.append("</array>");
            }
        }
        // 如果是简单类型
        else
        {
            // String simpleName = obj.getClass().getSimpleName();
            reqBode.append("<" + key + ">");
            reqBode.append(obj);
            reqBode.append("</" + key + ">");
        }
    }

    private boolean isComplexObject(Object obj)
    {
        if (obj instanceof BaseVO || obj.getClass().isArray())
        {
            return true;
        }
        return false;
    }

    /**
     * 解析登录响应消息
     * 
     * @param rspString
     *            响应消息体
     * @return 【返回码，userid】
     */
    public String[] parseLoginRsp(String rspString)
    {
        try
        {
            Document document = DocumentHelper.parseText(rspString);
            Element root = document.getRootElement();
            String resultCode = root.attributeValue("resultCode");
            String userID = root.elementText("string");
            return new String[]{resultCode, userID};
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 解析公共响应体，只获取返回码 <br>
     * 格式如： <result resultCode="0"> </result>
     * 
     * @param response
     *            响应xml消息体
     * @return 返回码
     */
    public String parseCommonRsp(String response)
    {
        try
        {
            Document document = DocumentHelper.parseText(response);
            Element root = document.getRootElement();
            String resultCode = root.attributeValue("resultCode");
            return resultCode;
        }
        catch (Exception e)
        {
            return "";
        }
    }

}
