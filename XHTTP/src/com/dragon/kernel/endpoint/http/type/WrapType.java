package com.dragon.kernel.endpoint.http.type;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract interface WrapType
{
	public abstract Object readObject(String paramString);

	public abstract Object doRead(DataInput in) throws IOException;

	public abstract void doWrite(DataOutput out, Object obj) throws IOException;
}