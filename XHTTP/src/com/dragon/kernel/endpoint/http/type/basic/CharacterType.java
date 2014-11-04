package com.dragon.kernel.endpoint.http.type.basic;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.dragon.kernel.endpoint.http.type.WrapType;

public class CharacterType implements WrapType
{
	public Object readObject(String value)
	{
		return value == null ? Character.valueOf("".charAt(0)) : Character.valueOf(value.charAt(0));
	}

	@Override
	public void doWrite(DataOutput out, Object obj) throws IOException
	{
		out.writeChar(null == obj ? "".charAt(0) : Character.valueOf(String.valueOf(obj).charAt(0)));
	}

	@Override
	public Object doRead(DataInput in) throws IOException
	{
		return in.readChar();
	}
}