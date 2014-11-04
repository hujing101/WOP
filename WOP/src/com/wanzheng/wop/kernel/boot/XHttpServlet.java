package com.wanzheng.wop.kernel.boot;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dragon.kernel.chain.Chain;
import com.dragon.kernel.endpoint.http.XHttp;
import com.dragon.kernel.endpoint.http.transform.MessageContext;
import com.dragon.kernel.endpoint.http.transform.Transform;
import com.dragon.kernel.endpoint.http.transform.TransformFactory;
import com.dragon.kernel.exc.XHttpException;
import com.wanzheng.wop.pub.bean.SpringBeanName;
import com.wanzheng.wop.pub.filter.AbsFiter;

/**
 * HTTP协议统一服务处理入口
 */
public class XHttpServlet extends HttpServlet
{
    private static final long serialVersionUID = 1706563505762179660L;
    
    private static final int XML_TRANSFORM = 1;
    
    private static final int OBJECT_TRANSFORM = 2;
    
    private static final String REQ_XML = "REQ_XML";
    
    private static final String RES_XML = "RES_XML";
    
    private static final String REQ_OBJECT = "REQ_OBJECT";
    
    private static final String RES_OBJECT = "RES_OBJECT";
    
    /**
     * RCS Logger
     */
    private static final Logger log = Logger.getLogger(XHttpServlet.class);
    
    private String xhttpBeanName = "xhttp";
    
    // 以下变量无需序列化
    
    private transient XHttp xhttp;
    
    private transient ServletController controler;
    
    private transient Chain systemChain;
    
    /**
     * {@inheritDoc}
     */
    public void init(ServletConfig servletConfig) throws ServletException
    {
        super.init(servletConfig);
        
        ApplicationContext appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletConfig.getServletContext());
        
        String configBeanName = servletConfig.getInitParameter("ControlerBeanName");
        
        xhttpBeanName = ((configBeanName != null) && (!"".equals(configBeanName.trim()))) ? configBeanName
                : xhttpBeanName;
        
        xhttp = (XHttp) appContext.getBean(xhttpBeanName, XHttp.class);
        
        controler = createController();
        
        try
        {
            systemChain = (Chain) appContext.getBean(SpringBeanName.SYSTEM_CHAIN);
        }
        catch (Exception e)
        {
            systemChain = null;
        }
        
    }
    
    /**
     *添加方法注释。
     * 
     * @return 返回
     * @throws ServletException
     *             异常
     */
    public ServletController createController() throws ServletException
    {
        return new ServletController(xhttp, getServletContext());
    }
    
    /**
     * {@inheritDoc}
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
    
    /**
     * {@inheritDoc}
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        this.process(request, response);
    }
    
    /**
     * 添加方法注释。
     * 
     * @param request
     *            参数
     * @param response
     *            参数
     * @throws ServletException
     *             参数
     * @throws IOException
     *             参数
     */
    private void process(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        
        MessageContext context = null;
        
        Transform transform = this.createTransform(request);
        
        controler.setTransform(transform);
        
        String serviceName = request.getPathInfo();
        
        // String reqApp = getService(request);
        
        // 前置系统过滤器执行
        if ((null == request.getQueryString() || request.getQueryString()
                .startsWith("Token"))
                && null != systemChain
                && null != systemChain.getBeforeFilter())
        {
            try
            {
                // 执行前置系统过滤链
                ((AbsFiter) systemChain.getBeforeFilter()).doBefore(request);
            }
            catch (XHttpException e)
            {
                if (log.isInfoEnabled())
                    log.error("Error occured from request "
                            + request.getPathInfo(),
                            e);
                
                context = new MessageContext(this.getService(request));
                context.setResultCode(e.getErrCode());
                
                try
                {
                    transform.produce(context);
                }
                catch (Throwable e1)
                {
                    throw new ServletException(e1);
                }
                finally
                {
                    response.getOutputStream()
                            .write(context.getResponseContent());
                }
                return;
            }
        }
        
        // 执行服务
        context = controler.doService(request, response);
        
        // 无论执行服务成功还是失败，都会执行后置系统过滤器
        if ((null == request.getQueryString() || request.getQueryString()
                .startsWith("Token"))
                && null != systemChain
                && null != systemChain.getAfterFilter()
                && !serviceName.contains("busy.jsp"))
        {
            doAfterFilter(transform, context);
        }
    }
    
    /**
     * 
     * <执行后置过滤器>
     * 
     * @param log
     * @param transform
     * @param context
     * @throws ServletException
     *             [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void doAfterFilter(Transform transform, MessageContext context)
            throws ServletException
    {
        try
        {
            systemChain.getAfterFilter().doAfter(context);
        }
        catch (XHttpException e)
        {
            context.setResultCode(e.getErrCode());
            
            try
            {
                transform.produce(context);
            }
            catch (Exception e1)
            {
                throw new ServletException(e1);
            }
            
            return;
        }
    }
    
    /**
     * 初始化解析器
     * 
     * @param request
     * 
     * @return Transform [返回类型说明]
     */
    private Transform createTransform(HttpServletRequest request)
    {
        if (null != request.getQueryString()
                && !"".equals(request.getQueryString()))
        {
            // 请求参数必须是：req_xml=method
            String[] params = request.getQueryString().split("=");
            
            return createTransformByQueryString(params[0]);
        }
        else
        {
            return createTransformByContentType(request.getHeader("Content-Type"));
        }
    }
    
    /**
     * 根据携带的参数初始化解析器
     * 
     * 
     * @param queryString
     * 
     * @return Transform [返回类型说明]
     */
    private Transform createTransformByQueryString(String queryString)
    {
        int type = XML_TRANSFORM;
        
        if (null != queryString)
        {
            if (queryString.equalsIgnoreCase(REQ_XML)
                    || queryString.equalsIgnoreCase(RES_XML))
            {
                type = XML_TRANSFORM;
            }
            else if (queryString.equalsIgnoreCase(REQ_OBJECT)
                    || queryString.equalsIgnoreCase(RES_OBJECT))
            {
                type = OBJECT_TRANSFORM;
            }
        }
        
        return TransformFactory.getInstance().create(type);
    }
    
    /**
     * 根据请求类型初始化解析器
     * 
     * @param contentType
     * 
     * @return Transform [返回类型说明]
     */
    private Transform createTransformByContentType(String contentType)
    {
        int type = XML_TRANSFORM;
        
        if (null != contentType && contentType.split("/").length > 1)
        {
            // contentType="text/xml; charset=UTF-8"
            if (contentType.split("/")[1].split(";")[0].equalsIgnoreCase("xml"))
            {
                type = XML_TRANSFORM;
            }
            // contentType="text/json; charset=UTF-8"
            else if (contentType.split("/")[1].split(";")[0].equalsIgnoreCase("object"))
            {
                type = OBJECT_TRANSFORM;
            }
        }
        
        return TransformFactory.getInstance().create(type);
    }
    
    /**
     * 提取服务名
     * 
     * 
     * @param request
     *            参数
     * 
     * @return String 服务名
     */
    protected String getService(HttpServletRequest request)
    {
        // pathInfo = "/appname"
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null)
        {
            return null;
        }
        
        String serviceName;
        
        if (pathInfo.startsWith("/"))
        {
            serviceName = pathInfo.substring(1);
        }
        else
        {
            serviceName = pathInfo;
        }
        
        return serviceName;
    }
}
