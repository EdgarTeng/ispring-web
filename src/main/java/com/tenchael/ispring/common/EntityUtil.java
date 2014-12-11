package com.tenchael.ispring.common;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntityUtil {

	public static String propertiesToString(final Object entity,
			String[] properties) {
		Map<String, Object> map = getPropertiesValue(entity, properties);
		Set<String> keys = map.keySet();
		StringBuffer buff = new StringBuffer();
		buff.append("{");
		for (String key : keys) {
			buff.append(key);
			buff.append("=");
			buff.append(map.get(key));
			buff.append(", ");
		}
		int index = buff.lastIndexOf(",");
		if (index > 0) {
			buff.deleteCharAt(index);
		}
		buff.append("}");
		return buff.toString();
	}

	public static Map<String, Object> getPropertiesValue(final Object entity,
			String[] properties) {
		Map<String, Object> map = new HashMap<String, Object>();
		Set<String> set = new HashSet<String>();
		for (String prop : properties) {
			set.add(prop);
		}
		try {
			Class clazz = entity.getClass();
			Field[] fields = clazz.getDeclaredFields();// 获得属性
			for (Field field : fields) {
				String fieldName = field.getName();
				if (set.contains(fieldName)) {
					PropertyDescriptor pd = new PropertyDescriptor(fieldName,
							clazz);
					Method getMethod = pd.getReadMethod();// 获得get方法
					Object value = getMethod.invoke(entity);// 执行get方法返回一个Object
					map.put(fieldName, value);
				}

			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return map;
	}

}
