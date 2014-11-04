package com.dragon.kernel.endpoint.http.type.basic;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.dragon.kernel.endpoint.http.type.WrapType;

public class BooleanType implements WrapType
{
	public Object readObject(String value)
	{
		return Boolean.valueOf(value == null ? false : Boolean.valueOf(value).booleanValue());
	}

	@Override
	public void doWrite(DataOutput out, Object obj) throws IOException
	{
		out.writeBoolean(null == obj ? false : (Boolean) obj);
	}

	@Override
	public Object doRead(DataInput in) throws IOException
	{
		return in.readBoolean();
	}
}