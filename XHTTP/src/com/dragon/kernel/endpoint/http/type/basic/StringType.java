package com.dragon.kernel.endpoint.http.type.basic;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.dragon.kernel.endpoint.http.type.WrapType;

public class StringType implements WrapType
{
	public Object readObject(String value)
	{
		return value == null ? "" : value;
	}

	@Override
	public void doWrite(DataOutput out, Object obj) throws IOException
	{
		out.writeUTF(null == obj ? "" : (String) obj);

	}

	@Override
	public Object doRead(DataInput in) throws IOException
	{
		return in.readUTF();
	}
}