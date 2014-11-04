package com.wanzheng.wop.kernel.mgmt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wanzheng.wop.kernel.boot.ConstantInit;

/**
 * 系统Spring加载管理类
 * 
 * 根据Bean的名称获取已经初始化的Bean对象 使用方法：
 * 
 * IUserService userService = (IUserService)
 * SJBMgmt.getInstance().getBean(SpringBeanName.USER_SERVICE);
 */
public final class SJBMgmt extends BaseMgmt
{
    
    private static SJBMgmt instance = new SJBMgmt();
    
    private ApplicationContext ctx;
    
    private Map<String, Object> beanNameMap = new HashMap<String, Object>();
    
    private final static byte[] lock = new byte[0];
    
    // private static Map<String, Object> mgmtMap = new HashMap<String,
    // Object>();
    
    // 初始化加载的顺序
    // private static String[] indexMgmt = new
    // String[]{SpringBeanName.CONFIG_MGMT};
    
    // public static String[] getIndexMgmt()
    // {
    // return indexMgmt;
    // }
    
    public SJBMgmt()
    {
        
    }
    
    /**
     * 
     * 添加方法注释。
     * 
     * @return 值
     */
    public static SJBMgmt getInstance()
    {
        if (null == instance)
        {
            synchronized (lock)
            {
                instance = new SJBMgmt();
            }
            
        }
        
        return instance;
    }
    
    /**
     * {@inheritDoc}
     */
    public void init(Properties props) throws Exception
    {
        loadApplicationContextXML(props);
        ConfigMgmt.getInstance().init(props);
        
        // 公共信息管理类
        CommonInfoMgmt.getInstance().init(props);
    }
    
    /**
     * 加载Spring对象
     * 
     * @param props
     *            [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void loadApplicationContextXML(Properties props) throws Exception
    {
        String path = "";
        
        try
        {
            ServletContext servletContext = (ServletContext) props.get(ConstantInit.APP_CONTEXT);
            
            if (servletContext != null)
            {
                ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
            }
            else
            {
                path = (String) props.get(ConstantInit.APP_CONTEXT_PATH);
                
                ctx = new FileSystemXmlApplicationContext(getConfigFiles(path));
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        
        if ((ctx == null) || (ctx.getBeanDefinitionNames().length == 0))
        {
            throw new NumberFormatException();
        }
    }
    
    private String[] getConfigFiles(String path)
    {
        List<String> temp = new ArrayList<String>();
        
        File file = new File(path);
        
        if (file.isDirectory())
        {
            File[] files = file.listFiles();
            
            for (int index = 0; index < files.length; index++)
            {
                String filePath = files[index].getPath();
                
                if (filePath.endsWith(".xml"))
                {
                    temp.add(filePath);
                }
            }
        }
        
        return (String[]) temp.toArray(new String[temp.size()]);
    }
    
    /**
     * 根据名称获取Bean对象<br>
     * 管理类直接通过实例获取
     * 
     * @param name
     *            Bean的名称
     * @return Object Bean对象实例
     */
    public Object getBean(String name)
    {
        // 判断是否为管理类实例,如果是则返回该实例
        // if (this.checkMgmtName(name))
        // {
        // return mgmtMap.get(name);
        // }
        if (ctx == null)
        {
            return null;
        }
        else
        {
            Object bean = beanNameMap.get(name);
            
            if (null == bean)
            {
                bean = ctx.getBean(name);
                
                beanNameMap.put(name, bean);
            }
            
            return bean;
        }
    }
    
    /**
     * 
     * 添加方法注释。
     * 
     * @param name
     *            参数
     * @return 返回值
     */
    @SuppressWarnings("unchecked")
    public Class getType(String name)
    {
        if (ctx == null)
        {
            return null;
        }
        else
        {
            return ctx.getType(name);
        }
    }
    
    /**
     * 获取上下文环境
     * 
     * 
     * @return ApplicationContext 上下文环境
     */
    public ApplicationContext getContext()
    {
        return ctx;
    }
    
    /**
     * 判断是否为管理类实例
     */
    // private boolean checkMgmtName(String name)
    // {
    // if (null == name || "".equals(name.trim()))
    // {
    // return false;
    // }
    //
    // String[] mgmtName = getIndexMgmt();
    //
    // for (int i = 0; i < mgmtName.length; i++)
    // {
    // if (mgmtName[i].equals(name.trim()))
    // {
    // return true;
    // }
    // }
    // return false;
    // }
}