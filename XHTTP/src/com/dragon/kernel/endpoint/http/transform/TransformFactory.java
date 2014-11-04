package com.dragon.kernel.endpoint.http.transform;

import java.util.HashMap;
import java.util.Map;

import com.dragon.kernel.endpoint.http.transform.object.ObjectTransform;
import com.dragon.kernel.endpoint.http.transform.object.common.CommonObjectTransform;
import com.dragon.kernel.endpoint.http.transform.xml.XMLTransform;

public final class TransformFactory
{
    private static final TransformFactory FACTORY = new TransformFactory();
    
    private Map<Integer, Transform> transforms = new HashMap<Integer, Transform>();
    
    public static TransformFactory getInstance()
    {
        return FACTORY;
    }
    
    public Transform create(int type)
    {
        Transform tf = (Transform) this.transforms.get(Integer.valueOf(type));
        
        if (tf == null)
        {
            switch (type)
            {
                case 1:
                    tf = new XMLTransform();
                    break;
                case 2:
                    tf = new ObjectTransform();
                    break;
                case 3:
                    tf = new CommonObjectTransform();
            }
            
            this.transforms.put(Integer.valueOf(type), tf);
        }
        
        return tf;
    }
}