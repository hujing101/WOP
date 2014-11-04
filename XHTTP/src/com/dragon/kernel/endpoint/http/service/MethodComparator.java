package com.dragon.kernel.endpoint.http.service;

import java.lang.reflect.Method;
import java.util.Comparator;

public class MethodComparator implements Comparator<Object>
{
	public int compare(Object o1, Object o2)
	{
		Method m1 = (Method) o1;

		Method m2 = (Method) o2;

		if (m1.getGenericParameterTypes().length > m2.getGenericParameterTypes().length)
		{
			return 1;
		}

		return -1;
	}
}