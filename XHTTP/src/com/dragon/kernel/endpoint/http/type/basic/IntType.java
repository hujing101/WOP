package com.dragon.kernel.endpoint.http.type.basic;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.dragon.kernel.endpoint.http.type.WrapType;

public class IntType implements WrapType
{
	public Object readObject(String value)
	{
		return value == null ? Integer.valueOf("0") : Integer.valueOf(value);
	}

	@Override
	public void doWrite(DataOutput out, Object obj) throws IOException
	{
		out.writeInt(null == obj ? 0 : (Integer) obj);

	}

	@Override
	public Object doRead(DataInput in) throws IOException
	{
		return in.readInt();
	}
}