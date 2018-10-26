package utils;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RefInvoke {

    public static Object createObject(Class clazz, Class[] parameterTypes, Object[] paramValues) {
        try {
            Constructor constructor = clazz.getConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(paramValues);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 多个参数
     * @param className
     * @param parameterTypes
     * @param paramValues
     * @return
     */
    public static Object createObject(String className, Class[] parameterTypes, Object[] paramValues) {
        try {
            Class clazz = Class.forName(className);
            return createObject(clazz, parameterTypes, paramValues);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 无参数的构造函数
     * @param className
     * @return
     */
    public static Object createObject(String className) {
        Class[] parameterTypes = new Class[] {};
        Object[] paramValues = new Object[] {};
        return createObject(className, parameterTypes, paramValues);
    }

    /**
     * 一个参数的构造函数
     * @param className
     * @return
     */
    public static Object createObject(String className, Class ParameterType, Object paramValue) {
        Class[] parameterTypes = new Class[] {ParameterType};
        Object[] paramValues = new Object[] {paramValue};
        return createObject(className, parameterTypes, paramValues);
    }


    /**
     * 多个参数
     * @param object
     * @param name
     * @param parameterTypes
     * @param parameterValues
     * @return
     */
    public static Object invokeInstanceMethod(Object object, String name, Class[] parameterTypes, Object[] parameterValues) {
        if (object == null) {
            return null;
        }

        try {
            Method method = object.getClass().getDeclaredMethod(name, parameterTypes);
            if (method == null) {
                return null;
            }
            method.setAccessible(true);
            return method.invoke(object, parameterValues);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 多个参数
     * @param object
     * @param name
     * @param parameterType
     * @param parameterValue
     * @return
     */
    public static Object invokeInstanceMethod(Object object, String name, Class parameterType, Object parameterValue) {
        if (object == null) {
            return null;
        }

        Class[] parameterTypes = new Class[] {parameterType};
        Object[] parameterValues = new Object[] {parameterValue};

        return invokeInstanceMethod(object, name, parameterTypes, parameterValues);
    }

    /**
     * 无参数
     * @param object
     * @param name
     * @return
     */
    public static Object invokeInstanceMethod(Object object, String name) {
        if (object == null) {
            return null;
        }

        Class[] parameterTypes = new Class[] {};
        Object[] parameterValues = new Object[] {};

        return invokeInstanceMethod(object, name, parameterTypes, parameterValues);
    }


    /**
     * 调用静态方法
     * @param name
     * @param parameterTypes
     * @param parameterValues
     * @return
     */
    public static Object invokeStaticMethod(String name, Class[] parameterTypes, Object[] parameterValues) {
        return invokeInstanceMethod(null, name, parameterTypes, parameterValues);
    }

    /**
     * 调用静态方法
     * @param name
     * @param parameterTypes
     * @param parameterValues
     * @return
     */
    public static Object invokeStaticMethod(String name, Class parameterTypes, Object parameterValues) {
        return invokeInstanceMethod(null, name, parameterTypes, parameterValues);
    }

    /**
     * 调用静态方法
     * @param name
     * @return
     */
    public static Object invokeStaticMethod(String name) {
        return invokeInstanceMethod(null, name);
    }

    public static Object getFiledObject(String className, String fieldName, Object obj) {
        try {
            Class clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFiledObject(String fieldName, Object obj) {
        try {
            Class clazz = obj.getClass();
            Field field = clazz.getField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getStaticFiledObject(String className,String fieldName) {
        return getFiledObject(className, fieldName, null);
    }

    public static void setFieldObject(String fieldName, Object object, Object fieldVal) {
        try {
            Class clazz = object.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, fieldVal);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setFieldObject(String className, String fieldName, Object object, Object fieldVal) {
        try {
            Class clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, fieldVal);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setStaticFieldObject(String className, String fieldName, String fieldVal) {
        try {
            Class clazz = Class.forName(className);
            Field field = clazz.getField(fieldName);
            field.setAccessible(true);
            field.set(null, fieldVal);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

