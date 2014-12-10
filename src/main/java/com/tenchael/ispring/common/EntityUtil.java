package com.tenchael.ispring.common;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntityUtil {

	public static String propertiesToString(final Object entity) {
		Map<String, Object> map = getGetMethodsValue(entity);
		Set<String> keys = map.keySet();
		StringBuffer buff = new StringBuffer();
		buff.append(entity.getClass().getSimpleName());
		buff.append(":[");
		for (String key : keys) {
			buff.append(key);
			buff.append("=");
			buff.append(map.get(key));
			buff.append(", ");
		}
		buff.append("]");
		return buff.toString();
	}

	public static Map<String, Object> getGetMethodsValue(final Object entity) {
		Map<String, Object> keyValue = new HashMap<String, Object>();
		try {
			Class clazz = entity.getClass();
			Field[] fields = clazz.getDeclaredFields();// 获得属性
			for (Field field : fields) {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
						clazz);
				Method getMethod = pd.getReadMethod();// 获得get方法
				Object value = getMethod.invoke(entity);// 执行get方法返回一个Object
				keyValue.put(field.getName(), value);
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
		return keyValue;
	}

}
