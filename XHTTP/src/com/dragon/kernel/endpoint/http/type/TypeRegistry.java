package com.dragon.kernel.endpoint.http.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class TypeRegistry
{
	private static final TypeRegistry	INSTANCE	= new TypeRegistry();

	private Map<String, WrapType>		types		= new HashMap<String, WrapType>();

	private TypeRegistry()
	{
		clear();
	}

	public static TypeRegistry getInstance()
	{
		return INSTANCE;
	}

	public void register(String key, WrapType type)
	{
		this.types.put(key, type);
	}

	public void unregister(String key)
	{
		this.types.remove(key);
	}

	public WrapType getType(String key)
	{
		return (WrapType) this.types.get(key);
	}

	public Collection<WrapType> getAllType()
	{
		return this.types.values();
	}

	public boolean hasType(String key)
	{
		return this.types.containsKey(key);
	}

	public void clear()
	{
		this.types.clear();
	}
}