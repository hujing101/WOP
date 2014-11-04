package com.dragon.kernel.endpoint.http.type.basic;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.dragon.kernel.endpoint.http.type.WrapType;

public class FloatType implements WrapType
{
	public Object readObject(String value)
	{
		return value == null ? Float.valueOf("0") : Float.valueOf(value);
	}

	@Override
	public void doWrite(DataOutput out, Object obj) throws IOException
	{
		out.writeFloat(null == obj ? 0 : (Float) obj);

	}

	@Override
	public Object doRead(DataInput in) throws IOException
	{
		return in.readFloat();
	}
}