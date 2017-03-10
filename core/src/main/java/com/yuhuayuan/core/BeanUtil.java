package com.yuhuayuan.core;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cl on 2017/3/8.
 */

public final class BeanUtil {
    public static Map<String, Object> describe(Object obj) throws IllegalAccessException {
        if(obj == null) {
            return new HashMap();
        } else {
            HashMap map = new HashMap();

            for(Class clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                Field[] fields = clazz.getDeclaredFields();
                Field[] var4 = fields;
                int var5 = fields.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    Field field = var4[var6];
                    field.setAccessible(true);
                    boolean isTransient = Modifier.isTransient(field.getModifiers());
                    if(!isTransient) {
                        String name = field.getName();
                        Object value = field.get(obj);
                        if(!map.containsKey(name)) {
                            map.put(name, value);
                        }
                    }
                }
            }

            return map;
        }
    }

    public static void populate(Object bean, Map<String, ? extends Object> properties) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        PropertyDescriptor[] var4 = propertyDescriptors;
        int var5 = propertyDescriptors.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor propertyDescriptor = var4[var6];
            String name = propertyDescriptor.getName();
            if(properties.containsKey(name)) {
                Object value = properties.get(name);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(bean, new Object[]{value});
            }
        }

    }

    private BeanUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

