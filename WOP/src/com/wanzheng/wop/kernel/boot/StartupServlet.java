package com.wanzheng.wop.kernel.boot;

import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.web.context.ContextLoader;

import com.wanzheng.wop.kernel.mgmt.ResConfig;
import com.wanzheng.wop.kernel.mgmt.SJBMgmt;

/**
 * Servlet implementation class StartupServlet
 */
public class StartupServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private ContextLoader contextLoader;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartupServlet()
    {
        super();
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);

        // 系统启动参数
        Properties props = new Properties();

        // 缓存context
        props.put(ConstantInit.APP_CONTEXT, config.getServletContext());
        String prefix = getServletContext().getRealPath("/");
        props.put(ConstantInit.APP_PATH, prefix);
        String log4jconfig = prefix + getInitParameter("log4jconfig");
        DOMConfigurator.configure(log4jconfig);
        Logger log = Logger.getLogger(getClass());
        if (log.isInfoEnabled())
        {
            log.info("log load sucess,path is " + log4jconfig);
        }
        this.contextLoader = createContextLoader();
        this.contextLoader.initWebApplicationContext(getServletContext());

        try
        {
            SJBMgmt.getInstance().init(props);

            // 加载配置文件
            String file = config.getServletContext().getRealPath(
                    "WEB-INF/config.properties");
            ResConfig.getInstance().load(file);
        }
        catch (Exception e)
        {
            if (log.isInfoEnabled())
                log.error(" System start error", e);
            System.exit(0);
            return;
        }
        if (log.isInfoEnabled())
            log.info(" System start sucess");

    }

    protected ContextLoader createContextLoader()
    {
        return new ContextLoader();
    }
}
