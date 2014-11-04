package com.dragon.kernel.object.serialize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface Writable
{
    public abstract void write(DataOutput out, Object obj) throws IOException;
    
    public abstract Object readFields(DataInput in) throws IOException;
}
