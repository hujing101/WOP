package com.dragon.kernel.endpoint.http.service;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ServiceFactory
{
	public Service create(Class<?> intf, Object serviceObject)
	{
		String serviceName = intf.getSimpleName();

		Service service = new Service(intf, serviceObject);

		service.setServiceName(serviceName);

		Method[] methods = intf.getDeclaredMethods();

		for (Method method : methods)
		{
			service.getMethod(method.getName()).add(method);
		}

		sort(service);

		return service;
	}

	private void sort(Service service)
	{
		Map<String, List<Method>> map = service.getMap();

		Iterator<Entry<String, List<Method>>> it = map.entrySet().iterator();

		while (it.hasNext())
		{
			Entry<String, List<Method>> entry = it.next();

			List<Method> methods = entry.getValue();

			Collections.sort(methods, new MethodComparator());
		}
	}
}