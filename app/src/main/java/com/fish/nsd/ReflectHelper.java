package com.fish.nsd;

import java.lang.reflect.Field;

/**
 * Created by fish on 16/8/5.
 */
public class ReflectHelper {
    public static Object getPrivateField(Object object, String fieldName) {
        Class cls = object.getClass();

        //getField只能得到可见的变量
        Field fd1 = null;//返回的值不是test对象的变量，而是Field对象。所以下面必须通过get方法传入指定的ReflectTest对象，获取值
        try {
            fd1 = cls.getField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        fd1.setAccessible(true);
        Object ret = null;
        try {
            ret = fd1.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
