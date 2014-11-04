package com.dragon.kernel.endpoint.http.type.basic;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.dragon.kernel.endpoint.http.type.WrapType;

public class LongType implements WrapType
{
	public Object readObject(String value)
	{
		return value == null ? Long.valueOf("0") : Long.valueOf(value);
	}

	@Override
	public void doWrite(DataOutput out, Object obj) throws IOException
	{
		out.writeLong(null == obj ? 0 : (Long) obj);

	}

	@Override
	public Object doRead(DataInput in) throws IOException
	{
		return in.readLong();
	}
}