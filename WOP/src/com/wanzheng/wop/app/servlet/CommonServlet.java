package com.wanzheng.wop.app.servlet;

import javax.servlet.http.HttpServlet;

import org.dom4j.Element;

/**
 * 普通servlet
 */
public class CommonServlet extends HttpServlet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    // protected ISpaceLog log =
    // ISpaceLogFactory.getLogUtils(LogModuleIDs.ISPACE_MANAGE_MODULE_NORMAL);

    private String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";

    /**
     * 检查ip
     * 
     * @param ipaccess
     *            ipaccess
     * @return boolean
     */
    protected boolean checkIps(String ipaccess)
    {
        // if (null == ipaccess || "".equals(ipaccess.trim()))
        // {
        // return false;
        // }
        //
        // // 从配置文件或者ip
        // ConfigMgmt config = (ConfigMgmt) SJBMgmt.getInstance().getBean(
        // SpringBeanName.CONFIG_MGMT);
        // String ipStr = config.getConfigValue(ConfigCon.IP_AUTH_FILTER);
        //
        // // 如果为空，则没有权限访问
        // if (null == ipStr || "".equals(ipStr.trim()))
        // {
        // return false;
        // }
        //
        // String[] ipList = ipStr.split("\\|", ipStr.length());
        //
        // // 没有权限
        // if (ipList.length == 0)
        // {
        // return false;
        // }
        //
        // for (int i = 0; i < ipList.length; i++)
        // {
        // // 如果存在，则返回true，可以访问
        //
        // if (ipaccess.trim().equals(ipList[i].trim()))
        // {
        return true;
        // }
        // }
        //
        // return false;
    }

    /**
     * 获取节点元素的值
     * 
     * @param resultCode
     *            结果码
     * @return String
     */
    protected String getResponseXML(String resultCode)
    {
        StringBuffer responseBuf = new StringBuffer(xmlHeader);
        responseBuf.append("<root>").append("<return>").append(resultCode)
                .append("</return>").append("</root>");
        String response = responseBuf.toString();
        responseBuf.delete(0, responseBuf.length());
        responseBuf.capacity();
        return response;
    }

    /**
     * 获取响应XML信息
     * 
     * @param resultCode
     *            结果码
     * @return String
     */
    protected String getResponseXMLInfo(String resultCode)
    {
        StringBuffer responseBuf = new StringBuffer(xmlHeader);
        responseBuf.append("<result resultCode=\"").append(resultCode).append(
                "\"></result>");
        String response = responseBuf.toString();
        responseBuf.delete(0, responseBuf.length());
        responseBuf.capacity();
        return response;
    }

    /**
     * 获取text值
     * 
     * @param element
     *            元素
     * @param name
     *            名
     * @return String
     */
    protected String getTextValue(Element element, String name)
    {
        if (null == element)
        {
            return "";
        }
        Element qelement = element.element(name);
        return qelement != null ? qelement.getText() : "";
    }
}
