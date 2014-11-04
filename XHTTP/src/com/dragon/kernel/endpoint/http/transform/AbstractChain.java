package com.dragon.kernel.endpoint.http.transform;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Locale;

public abstract class AbstractChain
{
	protected static final Object	DEFAULTVALUE	= "0";

	protected int					length			= 2;

	protected final Class<Object>	defaultClass	= Object.class;

	protected String getName(String simpleName)
	{
		return simpleName.substring(0, 1).toLowerCase(Locale.getDefault()) + simpleName.substring(1);
	}

	protected boolean isBasicType(Class<?> clz, String pkgName)
	{
		return (clz.isPrimitive()) || ((clz.getPackage() != null) && (clz.getPackage().getName().equals(pkgName)));
	}

	protected boolean isObject(Class<?> clz, String pkgName)
	{
		return (!clz.isArray()) && (!clz.isPrimitive()) && (clz.getPackage() != null) && (!clz.getPackage().getName().equals(pkgName));
	}

	protected boolean isParameterizedType(Type paramType, String intfPkg, String implPkg)
	{
		return ((paramType instanceof ParameterizedType))
				&& ((paramType.toString().startsWith(intfPkg)) || (paramType.toString().startsWith(implPkg)));
	}

	protected boolean isListOrMap(Class<?> clz, String intfPkg, String implPkg)
	{
		return (clz.getName().equals(intfPkg)) || (clz.getName().equals(implPkg));
	}
}