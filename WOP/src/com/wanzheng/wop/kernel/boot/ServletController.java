package com.wanzheng.wop.kernel.boot;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.dragon.kernel.constants.ErrorCode;
import com.dragon.kernel.endpoint.http.XHttp;
import com.dragon.kernel.endpoint.http.invoker.Invoker;
import com.dragon.kernel.endpoint.http.invoker.ServiceInvoker;
import com.dragon.kernel.endpoint.http.service.Service;
import com.dragon.kernel.endpoint.http.transform.MessageContext;
import com.dragon.kernel.endpoint.http.transform.Transform;
import com.wanzheng.wop.kernel.exc.WOPException;
import com.wanzheng.wop.pub.util.ServletUtil;

/**
 * 控制器
 */
public class ServletController
{
    
    private Logger log = Logger.getLogger(ServletController.class);
    
    /**
     * responses
     */
    private static ThreadLocal<HttpServletResponse> responses = new ThreadLocal<HttpServletResponse>();
    
    /**
     * requests
     */
    private static ThreadLocal<HttpServletRequest> requests = new ThreadLocal<HttpServletRequest>();
    
    /**
     * servletContext
     */
    protected ServletContext servletContext;
    
    /**
     * xhttp
     */
    private XHttp xhttp;
    
    /**
     * transform
     */
    private Transform transform;
    
    /**
     * 
     * 构造函数。
     * 
     * @param xhttp
     *            xhttp
     * @param servletContext
     *            servletContext
     */
    public ServletController(XHttp xhttp, ServletContext servletContext)
    {
        this.xhttp = xhttp;
        this.servletContext = servletContext;
    }
    
    /**
     * 设置requests
     * 
     * @return 返回requests
     */
    public static ThreadLocal<HttpServletRequest> getRequests()
    {
        return requests;
    }
    
    /**
     * 设置responses
     * 
     * @return 返回responses
     */
    public static ThreadLocal<HttpServletResponse> getResponses()
    {
        return responses;
    }
    
    /**
     * getTransform
     * 
     * @return 返回 transform
     */
    public Transform getTransform()
    {
        return transform;
    }
    
    /**
     * setTransform
     * 
     * @param transform
     *            对transform进行赋值
     */
    public void setTransform(Transform transform)
    {
        this.transform = transform;
    }
    
    public static HttpServletRequest getRequest()
    {
        return requests.get();
    }
    
    public static HttpServletResponse getResponse()
    {
        return responses.get();
    }
    
    /**
     * 执行服务入口
     * 
     * @param request
     *            request
     * @param response
     *            response
     * @throws ServletException
     *             ServletException
     * @throws IOException
     *             IOException
     * 
     * @return MessageContext MessageContext
     */
    protected MessageContext doService(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        MessageContext context = null;
        
        try
        {
            // 解析URL中的服务名
            
            String servicename = ServletUtil.getService(request);
            
            // 生成请求和响应规范
            if (isXJDLRequest(request))
            {
                generateXJDL(response, request.getQueryString(), servicename);
                if (log.isDebugEnabled())
                    log.debug("generateXJDL is Succeed");
            }
            else
            {
                requests.set(request);
                responses.set(response);
                
                // 执行服务
                context = invoke(request, response, servicename);
                byte[] responsebyte = context.getResponseContent();
                responses.get().getOutputStream().write(responsebyte);
                if (log.isDebugEnabled())
                    log.debug("invoke Succeed");
            }
        }
        finally
        {
            requests.set(null);
            responses.set(null);
        }
        
        return context;
    }
    
    /**
     * 判断请求是否需要构造规范页面
     * 
     * 
     * @param request
     *            request
     * 
     * @return boolean boolean
     */
    protected boolean isXJDLRequest(HttpServletRequest request)
    {
        //修改支持传参数
        String param = request.getQueryString();
        if (null == param || "".equals(param))
        {
            return false;
        }
        return param.startsWith("req_xml=") || param.startsWith("res_xml=");
    }
    
    /**
     * 生成请求和响应规范
     * 
     * 
     * @param response
     *            response
     * @param queryString
     *            URL中的请求参数，格式为：
     * 
     *            XML请求格式规范：REQ_XML=methodName XML响应格式规范：RES_XML=methodName
     *            JSON请求格式规范：REQ_JSON=methodName JSON响应格式规范：RES_JSON=methodName
     * @param servicename
     *            servicename
     * @throws ServletException
     *             ServletException
     * @throws IOException
     *             IOException
     * 
     */
    protected void generateXJDL(HttpServletResponse response,
            String queryString, String servicename) throws ServletException,
            IOException
    {
        Service service = xhttp.findService(servicename);
        
        // 解析参数，必须有等号
        String[] params = queryString.split("=");
        
        // 若请求参数不合法
        if (params.length != 2)
        {
            throw new ServletException("Wrong query string.");
        }
        
        try
        {
            if (params[0].toLowerCase(Locale.getDefault()).endsWith("xml"))
            {
                response.setContentType("text/plain");
            }
            
            transform.generateXJDL(response.getOutputStream(),
                    params[0],
                    service,
                    params[1]);
        }
        catch (Exception e)
        {
            // 生成规范失败
            throw new ServletException(e);
        }
    }
    
    /**
     * 执行服务
     * 
     * @param request
     *            request
     * @param response
     *            response
     * @param servicename
     *            servicename
     * @throws ServletException
     *             ServletException
     * @throws IOException
     *             IOException
     * 
     * @return MessageContext MessageContext
     */
    protected MessageContext invoke(HttpServletRequest request,
            HttpServletResponse response, String servicename)
            throws ServletException, IOException
    {
        MessageContext context = new MessageContext(servicename);
        InputStream input = request.getInputStream();
        byte[] b = new byte[1024];
        int size = 0;
        byte[] resultb = new byte[0];
        while ((size = input.read(b)) != -1)
        {
            byte[] temp = new byte[resultb.length + size];
            System.arraycopy(resultb, 0, temp, 0, resultb.length);
            System.arraycopy(b, 0, temp, resultb.length, size);
            resultb = temp;
        }
        context.setBinput(resultb);
        // 根据服务名获取服务对象
        
        Service service = xhttp.findService(servicename);
        
        context.setService(service);
        
        Invoker invoker = ServiceInvoker.getInstance();
        
        try
        {
            
            // 解析请求参数
            transform.consume(context);
            
            // 调用服务执行
            invoker.invoke(context);
            context.setResultCode(com.wanzheng.wop.app.contants.ErrorCode.SUCCESS);
            
            //构造返回消息体
            transform.produce(context);
            
        }
        catch (Exception e2)
        {
            Throwable e = e2.getCause();
            if (e instanceof WOPException)
            {
                
                final WOPException sepException = (WOPException) e;
                context.setResultCode(sepException.getCode());
                if (log.isInfoEnabled())
                {
                    log.info("ServletControl.invoke", e2);
                }
                
            }
            // 调用服务失败，目标服务不存在
            else if (e instanceof InvocationTargetException)
            {
                context.setResultCode(ErrorCode.ERROR_INVOCATE_SERVICE);
                if (log.isInfoEnabled())
                {
                    log.info("service does not exist", e2);
                }
            }
            // 执行服务返回与业务相关的错误码
            // XML文档格式不合法
            else
            {
                
                /**
                 * 由于存在多层代理的情况，业务异常或及根本异常原因信息会被封装为多层<br>
                 * 通过while循环获取业务异常或及根本异常原因信息
                 */
                boolean isWopException = false;
                Throwable exIn = e != null ? e.getCause() : null;
                while (exIn != null)
                {
                    if (exIn instanceof com.wanzheng.wop.kernel.exc.WOPException)
                    {
                        WOPException wopException = (WOPException) exIn;
                        context.setResultCode(wopException.getCode());
                        isWopException = true;
                        if (log.isInfoEnabled())
                        {
                            log.info("invoke service exception", exIn);
                        }
                        
                        break;
                    }
                    else
                    {
                        exIn = exIn.getCause();
                    }
                }
                
                if (!isWopException)
                {
                    context.setResultCode(ErrorCode.ERROR_XML_FORMAT);
                    if (log.isInfoEnabled())
                    {
                        log.info("invoke service exception", e2);
                    }
                }
                
            }
            
            try
            {
                // 封装失败响应消息体
                transform.produce(context);
            }
            catch (Exception ex)
            {
                throw new ServletException(ex);
            }
        }
        finally
        {
            
        }
        return context;
    }
    
    public void setXhttp(XHttp xhttp)
    {
        this.xhttp = xhttp;
    }
    
    public XHttp getXhttp()
    {
        return this.xhttp;
    }
}