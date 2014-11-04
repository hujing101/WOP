package com.wanzheng.wop.pub.util;


/**
 * 
 */
public class PicOperation
{
    //    
    //    /**
    //     * <生成新的图片>
    //     * <功能详细描述>
    //     * @param newWidth 新图片长度
    //     * @param newHeight 新图片高度
    //     * @param originalFilePath 原图片路径
    //     * @param newFilePath 新图片路径
    //     */
    //    public static void createNewPic(int newWidth, int newHeight,
    //            String originalFilePath, String newFilePath)
    //    {
    //        try
    //        {
    //            createLargeMin(newWidth, originalFilePath, newFilePath);
    //        }
    //        catch (Throwable e)
    //        {
    //            
    //        }
    //    }
    //    
    //    private static void createNewPicAndCatalog(String newFilePath)
    //    {
    //        File newCatalogFile = null;
    //        int lastBacklashLoaction = newFilePath.lastIndexOf(File.separator);//最后反斜线的位置
    //        
    //        if (lastBacklashLoaction != -1)
    //        {
    //            String newCatalog = newFilePath.substring(0, lastBacklashLoaction);
    //            newCatalogFile = new File(newCatalog);
    //            if (!newCatalogFile.exists() && !newCatalogFile.mkdirs())
    //            {
    //                //创建目录
    //            }
    //        }
    //    }
    //    
    //    /**
    //     * 支持自定义大小,文件格式,颜色,裁减方式(左上角,中间),同时返回URL
    //     * @param newWidth 裁剪后宽度
    //     * @param newHeight 裁剪后高度
    //     * @param typeColor 过滤类型 1.原图(默认) 2.黑白图
    //     * @param typeCut 裁减类型:1.左上角(默认) 2.中间
    //     * @param orgFilePath 原图路径
    //     * @param newFilePath 新图路径
    //     * @param pngSuffixType png格式类型 0：24位 1：8位
    //     * @return String [返回类型说明]
    //     * @see [类、类#方法、类#成员]
    //     * modify by longhuan version：L20040
    //     * 添加一个参数pngSuffixType，用来判断客户端请求的是png8格式的图片还是png24格式的图片
    //     */
    //    public static String createNewPic(int newWidth, int newHeight,
    //            int typeColor, int typeCut, String orgFilePath, String newFilePath,
    //            int pngSuffixType)
    //    {
    //        
    //        File newImgFile = new File(newFilePath);
    //        File oldImageFile = null;
    //        AffineTransform transform = null;
    //        AffineTransformOp ato = null;
    //        try
    //        {
    //            oldImageFile = new File(orgFilePath);
    //            
    //            //创建缩略图文件目录
    //            
    //            createNewPicAndCatalog(newFilePath);
    //            
    //            int nowWidth = newWidth;//缩小后要裁剪的宽度
    //            
    //            int nowHeight = newHeight;//缩小后要裁剪的高度
    //            
    //            int newWidths = newWidth;//缩小后的实际宽度
    //            int newHeights = newHeight;//缩小后的实际高度
    //            int top = 0;
    //            int left = 0;
    //            
    //            BufferedImage originalBI = ImageIO.read(oldImageFile);//读取文件缓存
    //            
    //            int originalPicWidth = originalBI.getWidth();//原来图片的大小 
    //            int originalPicHeight = originalBI.getHeight();//原来图片的高度
    //            
    //            //传入来的新图高度参数小于0,按比较算出新图的高度
    //            nowHeight = (newHeight <= 0) ? ((nowWidth * originalPicHeight) / originalPicWidth)
    //                    : newHeight;
    //            
    //            transform = new AffineTransform();// 
    //            
    //            double widthRate = (double) nowWidth / originalPicWidth;//压缩比率
    //            double heighRate = (double) nowHeight / originalPicHeight;//压缩比率 
    //            
    //            if (originalPicHeight <= originalPicWidth)
    //            {
    //                newWidths = originalPicWidth * nowHeight / originalPicHeight;
    //                if (newWidths < newWidth)
    //                {
    //                    heighRate = widthRate;
    //                    newWidths = 2 * newWidth - newWidths;
    //                }
    //                else
    //                {
    //                    widthRate = heighRate;
    //                    if (typeCut == 2)
    //                    {
    //                        //从中间上载剪坐标
    //                        top = (newWidths - newWidth) / 2;
    //                    }
    //                }
    //            }
    //            else
    //            {
    //                newHeights = originalPicHeight * nowWidth / originalPicWidth;
    //                if (newHeights < newHeight)
    //                {
    //                    widthRate = heighRate;
    //                    newHeights = 2 * newHeight - newHeights;
    //                }
    //                else
    //                {
    //                    heighRate = widthRate;
    //                    if (typeCut == 2)
    //                    {
    //                        //从中间左载剪坐标
    //                        left = (newHeights - newHeight) / 2;
    //                    }
    //                }
    //            }
    //            
    //            //长和宽按相同倍率缩放
    //            transform.setToScale(widthRate, heighRate);
    //            ato = new AffineTransformOp(transform,
    //                    AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    //            
    //            String sub = oldImageFile.getName()
    //                    .substring(oldImageFile.getName().lastIndexOf('.') + 1);
    //            
    //            //复杂度
    //            
    //            createNewPicCom(newWidth,
    //                    newHeight,
    //                    typeColor,
    //                    orgFilePath,
    //                    newFilePath,
    //                    newImgFile,
    //                    ato,
    //                    newWidths,
    //                    newHeights,
    //                    top,
    //                    left,
    //                    originalBI,
    //                    sub,
    //                    pngSuffixType);
    //        }
    //        catch (IOException e)
    //        {
    //            return "0";
    //        }
    //        finally
    //        {
    //            transform = null;
    //            ato = null;
    //            oldImageFile = null;
    //        }
    //        return newFilePath;
    //    }
    //    
    //    private static void createNewPicCom(int newWidth, int newHeight,
    //            int typeColor, String orgFilePath, String newFilePath,
    //            File newImgFile, AffineTransformOp ato, int newWidths,
    //            int newHeights, int top, int left, BufferedImage originalBI,
    //            String sub, int pngSuffixType) throws IOException
    //    {
    //        
    //        if ("png".equalsIgnoreCase(sub))
    //        {
    //            pngPic(newWidth,
    //                    newHeight,
    //                    typeColor,
    //                    newImgFile,
    //                    ato,
    //                    newWidths,
    //                    newHeights,
    //                    top,
    //                    left,
    //                    originalBI,
    //                    pngSuffixType);
    //        }
    //        else if ("bmp".equalsIgnoreCase(sub))
    //        {
    //            bmpPic(newWidth,
    //                    newHeight,
    //                    typeColor,
    //                    orgFilePath,
    //                    newImgFile,
    //                    ato,
    //                    newWidths,
    //                    newHeights,
    //                    top,
    //                    left,
    //                    originalBI);
    //        }
    //        else if ("gif".equalsIgnoreCase(sub))
    //        {
    //            gifPic(newWidth,
    //                    newHeight,
    //                    typeColor,
    //                    newFilePath,
    //                    top,
    //                    left,
    //                    originalBI);
    //        }
    //        else
    //        {
    //            BufferedImage newBI = null;
    //            newBI = new BufferedImage(newWidths, newHeights,
    //                    BufferedImage.TYPE_3BYTE_BGR);
    //            ato.filter(originalBI, newBI);
    //            //判断图片是否需要变色--modify by longhuan version:L20040
    //            if (typeColor == 2)
    //            {
    //                newBI = grayPic(newBI);
    //            }
    //            ImageIO.write(cutPic(newBI, top, left, newWidth, newHeight),
    //                    "jpg",
    //                    newImgFile);
    //        }
    //    }
    //    
    //    /**
    //     * <一句话功能简述>
    //     * <功能详细描述>
    //     * @param newWidth 裁剪的宽度
    //     * @param newHeight 裁剪的高度
    //     * @param typeColor  滤镜类型: 1.原图(默认) 2.黑白图
    //     * @param newImgFile 要写入图片的新文件
    //     * @param ato 
    //     * @param newWidths 原图缩放后的宽度
    //     * @param newHeights 原图缩放后的高度
    //     * @param top 
    //     * @param left
    //     * @param originalImg
    //     * @param pngSuffixType 请求的png格式，0代表24位格式，1代表8位格式
    //     * @throws IOException [参数说明]
    //     * @return void [返回类型说明]
    //     * @exception throws [违例类型] [违例说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    private static void pngPic(int newWidth, int newHeight, int typeColor,
    //            File newImgFile, AffineTransformOp ato, int newWidths,
    //            int newHeights, int top, int left, BufferedImage originalImg,
    //            int pngSuffixType) throws IOException
    //    {
    //        //----modify by longhuan version:L20040
    //        //如果请求的是png8格式的图片，则把原图写进生成的8位格式的BufferedImage对象中，
    //        //然后将对象进行转换源到新文件，并对这个对象进行裁剪
    //        int imageType = (pngSuffixType == 0 ? BufferedImage.TYPE_INT_ARGB_PRE
    //                : BufferedImage.TYPE_BYTE_INDEXED);
    //        BufferedImage newBI = new BufferedImage(newWidths, newHeights,
    //                imageType);
    //        BufferedImage originalBI = new BufferedImage(originalImg.getWidth(),
    //                originalImg.getHeight(), imageType);
    //        //判断图片是否需要变色
    //        if (typeColor == 2)
    //        {
    //            originalImg = grayPic(originalImg);
    //        }
    //        Graphics graphics = originalBI.createGraphics();
    //        graphics.drawImage(originalImg, 0, 0, null);
    //        //-----modify by longhuan end----
    //        ato.filter(originalBI, newBI);
    //        if (newWidth >= newHeight)
    //        {
    //            if (newWidths > newHeights)
    //            {
    //                newBI.getGraphics().drawImage(originalBI,
    //                        0,
    //                        0,
    //                        newWidth,
    //                        newWidth,
    //                        newBI.getGraphics().getColor(),
    //                        null);
    //            }
    //            else
    //            {
    //                newBI.getGraphics().drawImage(originalBI,
    //                        0,
    //                        0,
    //                        newWidths,
    //                        newHeights,
    //                        newBI.getGraphics().getColor(),
    //                        null);
    //            }
    //        }
    //        else
    //        {
    //            newBI.getGraphics().drawImage(originalBI,
    //                    0,
    //                    0,
    //                    newHeight,
    //                    newHeight,
    //                    newBI.getGraphics().getColor(),
    //                    null);
    //        }
    //        ImageIO.write(cutPic(newBI, top, left, newWidth, newHeight),
    //                "png",
    //                newImgFile);
    //    }
    //    
    //    private static void bmpPic(int newWidth, int newHeight, int typeColor,
    //            String orgFilePath, File newImgFile, AffineTransformOp ato,
    //            int newWidths, int newHeights, int top, int left,
    //            BufferedImage originalBI) throws IOException
    //    {
    //        BufferedImage destImg;
    //        switch (getBmpBit(orgFilePath))
    //        {
    //            case 1:
    //                destImg = new BufferedImage(newWidths, newHeights,
    //                        BufferedImage.TYPE_BYTE_BINARY);
    //                break;
    //            case 16:
    //                destImg = new BufferedImage(newWidths, newHeights,
    //                        BufferedImage.TYPE_USHORT_565_RGB);
    //                break;
    //            case 24:
    //                destImg = new BufferedImage(newWidths, newHeights,
    //                        BufferedImage.TYPE_3BYTE_BGR);
    //                break;
    //            default:
    //                destImg = new BufferedImage(newWidths, newHeights,
    //                        BufferedImage.TYPE_3BYTE_BGR);
    //                break;
    //        }
    //        ato.filter(originalBI, destImg);
    //        if (newWidth >= newHeight)
    //        {
    //            if (newWidths > newHeights)
    //            {
    //                //destImg.getGraphics().drawImage(originalBI, 0, 0, newWidth, newWidth, destImg.getGraphics().getColor(), null);
    //            }
    //            else
    //            {
    //                destImg.getGraphics().drawImage(originalBI,
    //                        0,
    //                        0,
    //                        newWidths,
    //                        newHeights,
    //                        destImg.getGraphics().getColor(),
    //                        null);
    //            }
    //        }
    //        else
    //        {
    //            destImg.getGraphics().drawImage(originalBI,
    //                    0,
    //                    0,
    //                    newHeight,
    //                    newHeight,
    //                    destImg.getGraphics().getColor(),
    //                    null);
    //        }
    //        //判断图片是否需要变色--modify by longhuan version:L20040
    //        if (typeColor == 2)
    //        {
    //            destImg = grayPic(destImg);
    //        }
    //        ImageIO.write(cutPic(destImg, top, left, newWidth, newHeight),
    //                "bmp",
    //                newImgFile);
    //    }
    //    
    //    private static void gifPic(int newWidth, int newHeight, int typeColor,
    //            String newFilePath, int top, int left,
    //            BufferedImage originalBI) throws IOException
    //    {
    //        BufferedImage destImg;
    //        destImg = new BufferedImage(newWidth, newHeight,
    //                BufferedImage.TYPE_4BYTE_ABGR);
    //        destImg.getGraphics().drawImage(originalBI,
    //                top,
    //                left,
    //                newWidth,
    //                newHeight,
    //                null);
    //        Gif89Encoder gifEncoder = null;
    //        FileOutputStream fileOutStream = null;
    //        try
    //        {
    //            //变成黑白色
    //            
    //            if (typeColor == 2)
    //            {
    //                gifEncoder = new Gif89Encoder(grayPic(destImg));
    //            }
    //            else
    //            {
    //                gifEncoder = new Gif89Encoder(destImg);
    //            }
    //            fileOutStream = new FileOutputStream(newFilePath);
    //            gifEncoder.encode(fileOutStream);
    //            ImageIO.write(destImg, "gif", fileOutStream);
    //        }
    //        catch (IOException e)
    //        {
    //            
    //        }
    //        finally
    //        {
    //            destImg = null;
    //            gifEncoder = null;
    //            if (null != fileOutStream)
    //            {
    //                fileOutStream.close();
    //            }
    //        }
    //    }
    //    
    //    /**
    //     * 对图片进行自定义的裁剪和变黑白
    //     * @param bufferImage []
    //     * @param top []
    //     * @param left []
    //     * @param newWidth []
    //     * @param newHeight []
    //     * @throws IOException [参数说明]
    //     * @return BufferedImage [返回类型说明]
    //     * @see [类、类#方法、类#成员]
    //     * modify by longhuan version:L20040
    //     * 将此方法做下修改，因为需要支持png8格式的原因，将对图片是否进行变色部分提取到上层pngPic(),createNewPicCom(),bmpPic()
    //     * 三个方法中。
    //     */
    //    public static BufferedImage cutPic(BufferedImage bufferImage, int top,
    //            int left, int newWidth, int newHeight) throws IOException
    //    {
    //        //对图片是否进行变色
    //        /*if (typeColor == 2)
    //         {
    //         bufferImage = grayPic(bufferImage);
    //         }*/
    //        Rectangle rectangle1 = new Rectangle(top, left, newWidth, newHeight);
    //        return bufferImage.getSubimage(rectangle1.x,
    //                rectangle1.y,
    //                rectangle1.width,
    //                rectangle1.height);
    //    }
    //    
    //    /**
    //     * <生成大缩略图>
    //     * <用户上传图片文件时，要大缩略图策略生成缩略图>
    //     * 
    //     * 策略
    //     * 规定大尺寸的缩略图的最大尺寸为：800×500，为保证缩略图不变形，需要依照原始图片按比例缩放。
    //     1）  如果原始图片的长（length）和宽（width）有任何一边的像素大于800个，则挑选其像素大的一边，
    //     将其缩放到800个像素。然后记下比率，ratio = max(length, width)/800，再将另外一边也按比率进行缩放。
    //     2）  如果原始图片的长（length）和宽（width）中没有任何一边的像素大于800个，则直接将原始图片作为其大缩略图。
    //     3）  对于静态图片，按照以上步骤来进行缩放，使用png格式编码，后缀名和原图后缀名一样。
    //     对于GIF动画，抽取其第一帧图片并进行缩放，产生的缩略图为jpg格式文件，后缀名是jpg。
    //     * @param baseValue 大缩略图的长度值，单位像素，如800px
    //     * @param originalFilePath 原图文件的完整物理路径
    //     * @param newFilePath 大缩略图的文件的完整物理路径，
    //     *                    非gif格式图片，/home/0/11/miniature/xxxx_large.xxx
    //     *                    gif格式图片，/home/0/11/miniature/xxxx_large.jpg
    //     * @lastmodify zhangshaojun 2008-08-07 [bmp，png大缩略图无法显示的问题]
    //     */
    //    public static void createLargeMiniature(int baseValue,
    //            String originalFilePath, String newFilePath)
    //    {
    //        try
    //        {
    //            createLargeMin(baseValue, originalFilePath, newFilePath);
    //        }
    //        catch (Throwable e)
    //        {
    //        }
    //    }
    //    
    //    /**
    //     * <生成大缩略图>
    //     * <用户上传图片文件时，要大缩略图策略生成缩略图>
    //     * 
    //     * 策略
    //     * 规定大尺寸的缩略图的最大尺寸为：800×500，为保证缩略图不变形，需要依照原始图片按比例缩放。
    //     1）  如果原始图片的长（length）和宽（width）有任何一边的像素大于800个，则挑选其像素大的一边，
    //     将其缩放到800个像素。然后记下比率，ratio = max(length, width)/800，再将另外一边也按比率进行缩放。
    //     2）  如果原始图片的长（length）和宽（width）中没有任何一边的像素大于800个，则直接将原始图片作为其大缩略图。
    //     3）  对于静态图片，按照以上步骤来进行缩放，使用png格式编码，后缀名和原图后缀名一样。
    //     对于GIF动画，抽取其第一帧图片并进行缩放，产生的缩略图为jpg格式文件，后缀名是jpg。
    //     * @param baseValue 大缩略图的长度值，单位像素，如800px
    //     * @param originalFilePath 原图文件的完整物理路径
    //     * @param newFilePath 大缩略图的文件的完整物理路径，
    //     *                    非gif格式图片，/home/0/11/miniature/xxxx_large.xxx
    //     *                    gif格式图片，/home/0/11/miniature/xxxx_large.jpg
    //     * @throws IOException 
    //     * @exception throws [违例类型] [违例说明]
    //     * @see [类、类#方法、类#成员]
    //     * @lastmodify zhangshaojun 2008-08-07 [bmp，png大缩略图无法显示的问题]
    //     */
    //    private static void createLargeMin(int baseValue, String originalFilePath,
    //            String newFilePath)
    //    {
    //        //mod by lingyu 20080626 for 大缩略全用jpg编码
    //        newFilePath = getMinitureFileName(newFilePath);
    //        //end lingyu
    //        
    //        File oriImgFile = new File(originalFilePath);
    //        
    //        if (!oriImgFile.exists())
    //        {
    //            return;
    //        }
    //        
    //        /* *** add by jiajingwen L20070 2011-01-17 start *** */
    //        //创建缩略图文件目录:缩略图的生成顺序是先生成小图,再生成大图,所以在生成小缩略图的时候创建缩略图文件目录
    //        //且此操作需要在原图存在时才做,否则之前就抛出异常
    //        if (Constants.PIC_MIN_LEN == baseValue)
    //        {
    //            createNewPicAndCatalog(newFilePath);
    //        }
    //        /* *** add by jiajingwen L20070 2011-01-17 end *** */
    //
    //        //读取原图
    //        //原图文件后缀名
    //        String sub = oriImgFile.getName().substring(oriImgFile.getName()
    //                .lastIndexOf('.') + 1);
    //        
    //        //具有可访问图像数据缓冲区的 Image
    //        BufferedImage origImage = null;
    //        int origWidth = 0;//原图宽
    //        
    //        int origHeight = 0;//原图高
    //        
    //        try
    //        {
    //            //从由原图物理路径创建的File对象中读出BufferedImage
    //            origImage = ImageIO.read(oriImgFile);
    //            if (null == origImage)
    //            {
    //                //1,如果JDK的ImageIO返回bufferedImage 为null，用原图做为大缩略图
    //                if ("gif".equalsIgnoreCase(sub))
    //                {
    //                    int index = newFilePath.lastIndexOf('.');
    //                    newFilePath = newFilePath.substring(0, index + 1) + "jpg";
    //                }
    //                //2,将原图复制到缩略图所在的路径
    //                copyFile(originalFilePath, newFilePath);
    //                //3,返回
    //                return;
    //            }
    //            origWidth = origImage.getWidth();
    //            origHeight = origImage.getHeight();
    //            
    //        }
    //        catch (Exception e)
    //        {
    //            return;
    //        }
    //        
    //        int newWidth = 0;//缩略图宽 
    //        int newHeight = 0;//缩略图高
    //        //        if (origWidth >= baseValue || origHeight >= baseValue)
    //        //        {
    //        // 原图象素大于800,生成大缩略图文件||原图象素大于100,生成小缩略图文件
    //        newWidth = (origWidth >= origHeight) ? baseValue : (origWidth
    //                * baseValue / origHeight);
    //        newHeight = (origWidth >= origHeight) ? (origHeight * baseValue / origWidth)
    //                : baseValue;
    //        //        }
    //        //        else
    //        //        {
    //        //            //象素小于800的，用原图做为大缩略图--当小缩略图调用此方法时,此流程仍然适用-jiajingwen
    //        //            if ("gif".equalsIgnoreCase(sub))
    //        //            {
    //        //                int index = newFilePath.lastIndexOf('.');
    //        //                newFilePath = newFilePath.substring(0, index + 1) + "jpg";
    //        //            }
    //        //            copyFile(originalFilePath, newFilePath);
    //        //            return;
    //        //        }
    //        int finalWidth = 0;
    //        int finalHeight = 0;
    //        if (origWidth < baseValue && origHeight < baseValue)
    //        {
    //            finalWidth = origWidth;
    //            finalHeight = origHeight;
    //        }
    //        else
    //        {
    //            finalWidth = newWidth;
    //            finalHeight = newHeight;
    //        }
    //        
    //        //原图象素大于800,生成大缩略图文件
    //        File newImgFile = new File(newFilePath);
    //        BufferedImage destImg = null;
    //        if ("gif".equalsIgnoreCase(sub))
    //        {
    //            //特殊处理GIF格式的动态图片，取图片的第一帔做为原图
    //            int index = newFilePath.lastIndexOf('.');
    //            String gifFilePath = newFilePath.substring(0, index + 1) + "jpg";
    //            destImg = new BufferedImage(finalWidth, finalHeight,
    //                    BufferedImage.TYPE_INT_RGB);
    //            //            destImg = new BufferedImage(newWidth, newHeight,
    //            //                    BufferedImage.TYPE_INT_RGB);
    //            destImg.getGraphics().drawImage(origImage,
    //                    0,
    //                    0,
    //                    finalWidth,
    //                    finalHeight,
    //                    null);
    //            //            destImg.getGraphics().drawImage(origImage,
    //            //                    0,
    //            //                    0,
    //            //                    newWidth,
    //            //                    newHeight,
    //            //                    null);
    //            //            Gif89Encoder gifEncoder = null;
    //            FileOutputStream fileOutStream = null;
    //            try
    //            {
    //                AffineTransform transform;
    //                transform = new AffineTransform();
    //                AffineTransformOp ato = new AffineTransformOp(transform,
    //                        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    //                ato.filter(origImage, destImg);
    //                //                gifEncoder = new Gif89Encoder(destImg);
    //                fileOutStream = new FileOutputStream(gifFilePath);
    //                //                gifEncoder.encode(fileOutStream);
    //                /* *** modified by jiajingwen L20070 2011-01-27 start *** */
    //                //修改图片绘制出来的格式为jpg
    //                ImageIO.write(destImg, "jpg", fileOutStream);
    //                /* *** modified by jiajingwen L20070 2011-01-27 end *** */
    //                //修改图片绘制出来的格式为jpg
    //            }
    //            catch (IOException e)
    //            {
    //                
    //                return;
    //            }
    //            finally
    //            {
    //                //gifEncoder = null;
    //                if (null != fileOutStream)
    //                {
    //                    try
    //                    {
    //                        fileOutStream.close();
    //                    }
    //                    catch (IOException e)
    //                    {
    //                        //do nothing
    //                    }
    //                }
    //            }
    //        }
    //        else
    //        {
    //            try
    //            {
    //                //除了GIF图片以外的其他格式的图片的缩略图生成
    //                createLargeMiniatureCom(originalFilePath,
    //                        sub,
    //                        origImage,
    //                        origWidth,
    //                        origHeight,
    //                        newWidth,
    //                        newHeight,
    //                        newImgFile,
    //                        baseValue);
    //            }
    //            catch (IOException e)
    //            {
    //                return;
    //            }
    //            catch (Exception e)
    //            {
    //                return;
    //            }
    //            finally
    //            {
    //                destImg = null;
    //                //oriImgFile = null;
    //            }
    //        }
    //    }
    //    
    //    /**
    //     * 除了GIF图片以外的其他格式的图片的缩略图生成
    //     * @param originalFilePath 原图文件的完整物理路径
    //     * @param sub 图片文件的后缀名
    //     * @param origImage 从原图物理位置读出的图片文件 {origImage = ImageIO.read(oriImgFile)}
    //     * @param origWidth 原图宽
    //     * @param origHeight 原图高
    //     * @param newWidth 缩略图宽
    //     * @param newHeight 缩略图高
    //     * @param newImgFile 缩略图文件
    //     * @throws IOException IOException
    //     */
    //    private static void createLargeMiniatureCom(String originalFilePath,
    //            String sub, BufferedImage origImage, int origWidth, int origHeight,
    //            int newWidth, int newHeight, File newImgFile, int baseValue)
    //            throws IOException
    //    {
    //        BufferedImage destImg;
    //        //AffineTransformOp使用仿射转换(AffineTransform)来执行从源图像到目标图像的线性映射
    //        AffineTransformOp ato;
    //        /*AffineTransform为2D 仿射变换，它执行从 2D 坐标到其他 2D 坐标的线性映射 
    //         可以使用一系列平移 (translation)、缩放 (scale)、翻转 (flip)、旋转 (rotation) 和错切 (shear) 来构造仿射变换*/
    //        AffineTransform transform;
    //        double widthRate = (double) newWidth / origWidth;//压缩比率
    //        double heighRate = (double) newHeight / origHeight;//压缩比率 
    //        transform = new AffineTransform();//AffineTransform其默认构造方法构造一个表示恒等变换的新 AffineTransform
    //        
    //        /*
    //         *将此变换设置为缩放变换。表示此变换的矩阵将变成： 
    //         [   widthRate    0        0   ]
    //         [      0     heighRate    0   ]
    //         [      0         0        1   ]
    //         */
    //        transform.setToScale(widthRate, heighRate);
    //        ato = new AffineTransformOp(transform,
    //                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    //        
    //        /* *** added by jiajingwen L20070 2011-02-15 start *** */
    //        //如果原图小于缩略图大小,生成图片时宽度和高度不变;如果原图大于缩略图大小,则按比例进行缩放
    //        int finalWidth = 0;
    //        int finalHeight = 0;
    //        if (origWidth < baseValue && origHeight < baseValue)
    //        {
    //            finalWidth = origWidth;
    //            finalHeight = origHeight;
    //        }
    //        else
    //        {
    //            finalWidth = newWidth;
    //            finalHeight = newHeight;
    //        }
    //        /* *** added by jiajingwen L20070 2011-02-15 end *** */
    //        if ("png".equalsIgnoreCase(sub))
    //        {
    //            /* *** modified by jiajingwen L20070 2011-02-15 start *** */
    //            destImg = new BufferedImage(finalWidth, finalHeight,
    //                    BufferedImage.TYPE_3BYTE_BGR);
    //            //            destImg = new BufferedImage(newWidth, newHeight,
    //            //                    BufferedImage.TYPE_3BYTE_BGR);
    //            ato.filter(origImage, destImg);
    //            destImg.getGraphics().drawImage(origImage,
    //                    0,
    //                    0,
    //                    finalWidth,
    //                    finalHeight,
    //                    destImg.getGraphics().getColor(),
    //                    null);
    //            /* *** modified by jiajingwen L20070 2011-02-15 end *** */
    //            /* *** modified by jiajingwen L20070 2011-01-27 start *** */
    //            //修改图片绘制出来的格式为jpg
    //            ImageIO.write(destImg, "jpg", newImgFile);
    //            /* *** modified by jiajingwen L20070 2011-01-27 end *** */
    //        }
    //        else if ("bmp".equalsIgnoreCase(sub))
    //        {
    //            //判断图片们数进行不同的取色
    //            switch (getBmpBit(originalFilePath))
    //            {
    //                case 1:
    //                    destImg = new BufferedImage(finalWidth, finalHeight,
    //                            BufferedImage.TYPE_BYTE_BINARY);
    //                    break;
    //                case 16:
    //                    destImg = new BufferedImage(finalWidth, finalHeight,
    //                            BufferedImage.TYPE_USHORT_565_RGB);
    //                    break;
    //                case 24:
    //                    destImg = new BufferedImage(finalWidth, finalHeight,
    //                            BufferedImage.TYPE_3BYTE_BGR);
    //                    break;
    //                default:
    //                    destImg = new BufferedImage(finalWidth, finalHeight,
    //                            BufferedImage.TYPE_3BYTE_BGR);
    //                    break;
    //            }
    //            /* *** modified by jiajingwen L20070 2011-02-15 start *** */
    //            //            destImg = new BufferedImage(finalWidth, finalHeight,
    //            //                    BufferedImage.TYPE_INT_RGB);
    //            //            destImg = new BufferedImage(newWidth, newHeight,
    //            //                    BufferedImage.TYPE_INT_RGB);
    //            ato.filter(origImage, destImg);
    //            destImg.getGraphics().drawImage(origImage,
    //                    0,
    //                    0,
    //                    finalWidth,
    //                    finalHeight,
    //                    destImg.getGraphics().getColor(),
    //                    null);
    //            /* *** modified by jiajingwen L20070 2011-02-15 end *** */
    //            /* *** modified by jiajingwen L20070 2011-01-27 start *** */
    //            //修改图片绘制出来的格式为jpg
    //            ImageIO.write(destImg, "jpg", newImgFile);
    //            /* *** modified by jiajingwen L20070 2011-01-27 end *** */
    //        }
    //        else
    //        {
    //            /* *** modified by jiajingwen L20070 2011-02-15 start *** */
    //            destImg = new BufferedImage(finalWidth, finalHeight,
    //                    BufferedImage.TYPE_3BYTE_BGR);
    //            //            destImg = new BufferedImage(newWidth, newHeight,
    //            //                    BufferedImage.TYPE_3BYTE_BGR);
    //            ato.filter(origImage, destImg);
    //            destImg.getGraphics().drawImage(origImage,
    //                    0,
    //                    0,
    //                    finalWidth,
    //                    finalHeight,
    //                    destImg.getGraphics().getColor(),
    //                    null);
    //            /* *** modified by jiajingwen L20070 2011-02-15 end *** */
    //            ImageIO.write(destImg, "jpg", newImgFile);
    //        }
    //    }
    //    
    //    /**
    //     <组装缩略图物理路径>
    //     <根据PhyfileID组装缩略图物理路径,包含第二级，第三级，不包含根目录
    //     第二层目录名为fileid的第一个字符，
    //     第三层目录名为fileid的第二个和第三个字符，fileid为物理文件名>
    //     * @param phyFileId 物理文件ID
    //     * @param subfix []
    //     * @return [0:小缩略图物理路径，1：大缩略图物理路径]
    //     * @throws StorageException  []
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static String[] getMiniaturePath(String phyFileId, String subfix)
    //            throws WOPException
    //    {
    //        if (null == phyFileId || "".equals(phyFileId))
    //        {
    //            throw new WOPException(ErrorCode.INVALID_FILEID,
    //                    ",phyfile id invalidate,merge miniature path fail", null);
    //        }
    //        if (subfix.lastIndexOf(".") == -1)
    //        {
    //            subfix = "." + subfix;
    //        }
    //        String[] miniaturePhyPath = new String[2];
    //        String dirSecond = phyFileId.substring(0, Constants.FC_LEN);
    //        String dirThird = phyFileId.substring(Constants.FC_LEN,
    //                Constants.SC_LEN);
    //        
    //        //小缩略图文件名
    //        
    //        String smallName = phyFileId + Constants.MINIATURELAST + subfix;
    //        miniaturePhyPath[0] = mergePath(dirSecond, dirThird, smallName);
    //        //大缩略图文件名
    //        
    //        String largeName = phyFileId + Constants.LARGEMINIATURELAST + subfix;
    //        miniaturePhyPath[1] = mergePath(dirSecond, dirThird, largeName);
    //        
    //        return miniaturePhyPath;
    //    }
    //    
    //    /**
    //     <组装自定义缩略图物理路径,新增>
    //     * @param rootDir 根目录
    //     * @param phyFileId 物理ID
    //     * @param oldSubfix 原文件后缀
    //     * @param newSuffix 新文件后缀
    //     * @param newSize 裁剪尺寸
    //     * @param cutType 裁减类型:1.左上角(默认) 2.中间
    //     * @param filterType 滤镜类型: 1.原图(默认) 2.黑白图
    //     * @param pngSuffixType 请求的png格式，0代表24位格式，1代表8位格式
    //     * @return [0:图片物理路径，1：缩略图物理路径]
    //     * @throws StorageException 异常
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static String[] getCustomerPath(String rootDir, String phyFileId,
    //            String oldSubfix, String newSuffix, int[] newSize, int cutType,
    //            int filterType, int pngSuffixType) throws WOPException
    //    {
    //        if (null == phyFileId || "".equals(phyFileId))
    //        {
    //            throw new WOPException(ErrorCode.INVALID_FILEID,
    //                    ",phyfile id invalidate,merge miniature path fail", null);
    //        }
    //        if (oldSubfix.lastIndexOf(".") == -1)
    //        {
    //            oldSubfix = "." + oldSubfix;
    //        }
    //        if (newSuffix.lastIndexOf(".") == -1)
    //        {
    //            newSuffix = "." + newSuffix;
    //        }
    //        String largeName = phyFileId;
    //        StringBuffer sbFile = new StringBuffer("");
    //        String[] miniaturePhyPath = new String[2];
    //        String dirSecond = largeName.substring(0, Constants.FC_LEN);
    //        String dirThird = largeName.substring(Constants.FC_LEN,
    //                Constants.SC_LEN);
    //        
    //        sbFile.append(rootDir);
    //        sbFile.append(File.separator);
    //        sbFile.append(dirSecond);
    //        sbFile.append(File.separator);
    //        sbFile.append(dirThird);
    //        sbFile.append(File.separator);
    //        //原文件路径
    //        
    //        miniaturePhyPath[0] = sbFile.toString() + largeName + oldSubfix;
    //        sbFile.append(Constants.MINIATUREDIRNAME);
    //        sbFile.append(File.separator);
    //        sbFile.append(largeName);
    //        sbFile.append("_" + newSize[0] + "_" + newSize[1]);
    //        sbFile.append("_" + cutType + "_" + filterType);
    //        //modify by longhuan version:L20040
    //        //如果要求生成png8格式的，则在文件名后面加上"_1",与png24格式的区别
    //        if (".png".equalsIgnoreCase(newSuffix) && 1 == pngSuffixType)
    //        {
    //            sbFile.append("_" + pngSuffixType);
    //        }
    //        sbFile.append(newSuffix);
    //        //新文件路径
    //        
    //        miniaturePhyPath[1] = sbFile.toString();
    //        return miniaturePhyPath;
    //    }
    //    
    //    /**
    //     * <合并目录>
    //     * <根据传入的目录参数，组装完整的目录路径>
    //     * @param dirSecond 第二级目录
    //
    //     * @param dirThird  第三级目录
    //
    //     * @param fileName  文件名
    //
    //     * @return String [相对于根目录的路径]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    private static String mergePath(String dirSecond, String dirThird,
    //            String fileName)
    //    {
    //        StringBuffer buf = new StringBuffer();
    //        buf.append(File.separator);
    //        buf.append(dirSecond);
    //        buf.append(File.separator);
    //        buf.append(dirThird);
    //        buf.append(File.separator);
    //        buf.append(Constants.MINIATUREDIRNAME);
    //        buf.append(File.separator);
    //        buf.append(fileName);
    //        return buf.toString();
    //    }
    //    
    //    /**
    //     * <拷贝文件>
    //     * <功能详细描述>
    //     * @param originalFile
    //     * @param destFile [参数说明]
    //     * 
    //     * @return void [返回类型说明]
    //     * @exception throws [违例类型] [违例说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    private static void copyFile(String originalFile, String destFile)
    //    {
    //        FileInputStream fin = null;
    //        FileOutputStream fou = null;
    //        
    //        try
    //        {
    //            fin = new FileInputStream(originalFile);
    //            fou = new FileOutputStream(destFile);
    //            byte[] b = new byte[Constants.BYTE_SIZE];
    //            while (-1 != fin.read(b))
    //            {
    //                fou.write(b);
    //            }
    //        }
    //        catch (FileNotFoundException e)
    //        {
    //        }
    //        catch (IOException e)
    //        {
    //            
    //        }
    //        finally
    //        {
    //            if (null != fin)
    //            {
    //                try
    //                {
    //                    fin.close();
    //                }
    //                catch (IOException e)
    //                {
    //                }
    //            }
    //            if (null != fou)
    //            {
    //                try
    //                {
    //                    fou.close();
    //                }
    //                catch (IOException e)
    //                {
    //                }
    //            }
    //        }
    //    }
    //    
    //    /**
    //     * 
    //     * 彩色转为黑白
    //     * <功能详细描述>
    //     * @param src []
    //     * @return BufferedImage [返回类型说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static BufferedImage grayPic(BufferedImage src)
    //    {
    //        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
    //        ColorConvertOp op = new ColorConvertOp(cs, null);
    //        return op.filter(src, null);
    //    }
    //    
    //    /**
    //     * 
    //     * <缩略图的文件名后棳>
    //     * <功能详细描述>
    //     * @param orgName 文件名
    //
    //     * @param picType 缩略图类型 0,小缩略图
    //     * @return String [返回类型说明]
    //     * @exception throws [违例类型] [违例说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    private static String getMinitureFileName(String orgName)
    //    {
    //        int tmp = orgName.lastIndexOf('.');
    //        if (tmp == -1)
    //        {
    //            return orgName;
    //        }
    //        String preName = orgName.substring(0, tmp);
    //        return preName + ".jpg";
    //        
    //    }
    //    
    //    /**
    //     * 用户判断bmp图片位数
    //     *
    //     * @param fph
    //     * 
    //     * @return int
    //     * @exception throws IOException
    //     */
    //    private static int getBmpBit(String fph) throws IOException
    //    {
    //        FileInputStream fs = null;
    //        
    //        try
    //        {
    //            fs = new FileInputStream(fph);
    //            long skipSize = fs.skip(14);
    //            if (skipSize != 14)
    //            {
    //                throw new IOException("skip bytes fail");
    //            }
    //            int biLen = 40;
    //            byte bi[] = new byte[biLen];
    //            long readLen = fs.read(bi, 0, biLen); // 读取40字节BMP信息头
    //            
    //            if (readLen != biLen)
    //            {
    //                throw new IOException(
    //                        "fewer bytes were read than the requested");
    //            }
    //            // 位数
    //            int nBitCount = (((int) bi[15] & 0xff) << 8) | (int) bi[14] & 0xff;
    //            
    //            return nBitCount;
    //        }
    //        finally
    //        {
    //            fs.close();
    //        }
    //    }
    //    
    //    /**
    //     * 
    //     * <对图片进行裁剪>
    //     * <将图片裁剪成正方形，从长边两侧裁剪>
    //     * @author zKF11191 20080725
    //     * @param oldImageFile []
    //     * @param newWidth []
    //     * @param newHeight []
    //     * @throws IOException [参数说明]
    //     * @return BufferedImage [返回类型说明]
    //     * @see [类、类#方法、类#成员]
    //     */
    //    public static BufferedImage cutPic(File oldImageFile, int newWidth,
    //            int newHeight) throws IOException
    //    {
    //        BufferedImage originalImg = ImageIO.read(oldImageFile);
    //        
    //        int x = originalImg.getWidth();
    //        int y = originalImg.getHeight();
    //        
    //        //如果小于待压缩后的大小，直接返回原图
    //        if (x <= newWidth && y <= newHeight)
    //        {
    //            return originalImg;
    //        }
    //        
    //        //长边两侧需要裁的长度
    //        int tmp = 0;
    //        
    //        //(矩形)Rectangle指定坐标空间中的一个区域，通过坐标空间中Rectangle对象左上方的点(x,y)、宽度和高度可以定义这个区域(矩形)
    //        Rectangle rectangle1 = null;
    //        
    //        if (x > y)
    //        {
    //            tmp = (x - y) / 2;
    //            rectangle1 = new Rectangle(tmp, 0, y, y);
    //        }
    //        else
    //        {
    //            tmp = (y - x) / 2;
    //            rectangle1 = new Rectangle(0, tmp, x, x);
    //        }
    //        
    //        BufferedImage bufimg = originalImg.getSubimage(rectangle1.x,
    //                rectangle1.y,
    //                rectangle1.width,
    //                rectangle1.height);
    //        
    //        //originalImg = null;
    //        rectangle1 = null;
    //        
    //        return bufimg;
    //        
    //    }
}
