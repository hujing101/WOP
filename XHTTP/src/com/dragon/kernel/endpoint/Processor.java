package com.dragon.kernel.endpoint;

import com.dragon.kernel.chain.Chain;

public abstract interface Processor
{
	public abstract void setServiceFilter(Chain paramChain);

	public abstract Chain getServiceFilter();
}