package com.dragon.kernel.constants;

public abstract interface XHttpConstants
{
    public static final String REQUEST_DATA = "request_data";
    
    public static final String SYSTEMCHAIN = "systemChain";
    
    public static final byte NULL_PARAM = 0x00;
    
    public static final byte NOT_NULL_PARAM = 0x01;
    
    public static final byte TC_OBJECT = 0X02;
    
    public static final byte TC_ARRAY = 0X03;
    
    public static final byte TC_LIST = 0X04;
    
    public static final byte TC_MAP = 0X05;
    
    public static final byte TC_PRIMITIVE = 0X07;
    
}