package com.dragon.kernel.endpoint.http.type.basic;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.dragon.kernel.endpoint.http.type.WrapType;

public class DoubleType implements WrapType
{
	public Object readObject(String value)
	{
		return value == null ? Double.valueOf("0") : Double.valueOf(value);
	}

	@Override
	public void doWrite(DataOutput out, Object obj) throws IOException
	{
		out.writeDouble(null == obj ? 0 : (Double) obj);
	}

	@Override
	public Object doRead(DataInput in) throws IOException
	{
		return in.readDouble();
	}
}