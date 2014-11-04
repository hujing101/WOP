package com.dragon.kernel.endpoint.http.transform.object;

import java.io.DataInput;
import java.io.DataOutput;
import java.lang.reflect.Type;

import com.dragon.kernel.endpoint.http.transform.AbstractChain;
import com.dragon.kernel.exc.XHttpException;

public abstract class OBJChain extends AbstractChain
{
	public abstract void addChain(OBJChain paramOBJChain);

	public abstract Object doRead(DataInput in, Type paramType, Class<?> clz) throws XHttpException;

	public abstract void doWrite(DataOutput out, Object value) throws XHttpException;

	public abstract OBJChain getChain();
}
