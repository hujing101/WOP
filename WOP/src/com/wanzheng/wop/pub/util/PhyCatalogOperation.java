package com.wanzheng.wop.pub.util;

/**
 * 
 * <处理文件的物理存储目录>
 * <目录包括有原文件的物理存储目录，图片的缩略图目录，生成目录路径>
 * 
 * @author  f00104432
 * @version  [版本号, 2007-9-10]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PhyCatalogOperation
{
    //    /**
    //     * </**
    //     * <生成文件物理目录>
    //     * <目录生成规则：文件的物理路径，目录结构分为三层，root为存储根目录，
    //
    //     *  其它两层由fileid拆分构造，第二层目录名为fileid的第一个字符，
    //     *  第三层目录名为fileid的第二个和第三个字符，fileid为物理文件名,不包含后缀名
    //     * >
    //     * @param savePath 存储根目录
    //     * @param phyFileId 存储的文件名称
    //     * @return String[] 内容实体的保存路径 [0.物理存储目录，1.物理存储文件名，不包含后缀名]
    //     * @throws StorageException StorageException
    //     */
    //    public static String[] createCatalog(String savePath, String phyFileId)
    //            throws WOPException
    //    {
    //        if (StringUtil.isEmpty(savePath))
    //        {
    //            throw new WOPException(ErrorCode.INVALID_ROOT,
    //                    "root directory is null.", null);
    //        }
    //        if (StringUtil.isEmpty(phyFileId.trim()))
    //        {
    //            throw new WOPException(ErrorCode.INVALID_FILEID,
    //                    "phyFileId  is not.", null);
    //        }
    //        
    //        //物理目录名和物理存储的文件ID
    //        String[] returnPath = new String[2];
    //        // GUID
    //        //        String guid = StorageUtil.getGUID();
    //        //创建目录时，先去掉号首这几位
    //        String guid = phyFileId;
    //        // 第二层目录        
    //        String directorySecond = guid.substring(0, Constants.FC_LEN);
    //        
    //        // 第三层目录
    //        
    //        String directoryThird = guid.substring(Constants.FC_LEN, Constants.SC_LEN);
    //        if (!"/".equals(savePath))
    //        {
    //            //根目录如：/home
    //            returnPath[0] = savePath + File.separator + directorySecond
    //                    + File.separator + directoryThird + File.separator;//物理文件目录
    //        }
    //        else
    //        {
    //            //根目录:如/
    //            returnPath[0] = File.separator + directorySecond + File.separator
    //                    + directoryThird + File.separator;//在根目录下
    //            
    //        }
    //        
    //        File file = new File(returnPath[0]);
    //        if (!file.exists())
    //        {
    //            boolean isSuc = file.mkdirs();//创建目录
    //            if (!isSuc)
    //            {
    //                throw new WOPException(ErrorCode.MKDIR_FAIL,
    //                        "create directory fail", null);
    //            }
    //        }
    //        //物理存储的文件ID ,phyFileId
    //        returnPath[1] = guid;
    //        return returnPath;
    //    }
    //    
    //
    //    /**
    //     * <创建缩略图的目录,并返回缩略图目录地址>
    //     * @param parentPath 原图的物理目录地址
    //     * @return String [返回类型说明:缩略图目录地址]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public String createMiniaturPath(String parentPath)
    //    {
    //        String returnPath = this.addSplash(parentPath);//加上/
    //        if (returnPath != null && !returnPath.equals("/"))
    //        { //根目录如：/home,f00104432
    //            returnPath = returnPath + Constants.MINIATUREDIRNAME
    //                    + File.separator;
    //        }
    //        else
    //        {
    //            returnPath = File.separator + Constants.MINIATUREDIRNAME
    //                    + File.separator;
    //        }
    //        
    //        File file = new File(returnPath);
    //        if (!file.exists())
    //        {
    //            boolean isSuc = file.mkdirs();
    //            if (!isSuc)
    //            {
    //                return null;
    //            }
    //        }
    //        return returnPath;
    //    }
    //    
    //    private String addSplash(String filePath)
    //    {
    //        String str = filePath;
    //        if (!StringUtil.isEmpty(filePath))
    //        {
    //            if (!filePath.endsWith("/"))
    //            {
    //                str = str + "/";
    //            }
    //        }
    //        return str;
    //    }
}
