package com.example.bonree.xposeddemo.xpose.util;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassUtil {
    /**
     * 打印类的信息，包括类的成员函数、成员变量(只获取成员函数)
     * Method类，方法对象
     * 一个成员方法就是一个Method对象
     * getMethods()方法获取的是所有的public的函数，包括父类继承而来的
     * getDeclaredMethods()获取的是所有该类自己声明的方法，不问访问权限
     */
    public static void printClassMethodMessage(Class c, String tag) {
        //要获取类的信息  首先要获取类的类类型
        //获取类的名称
        StringBuilder builder = new StringBuilder();

        Method[] ms = c.getMethods();//c.getDeclaredMethods()
        for (int i = 0; i < ms.length; i++) {
            //得到方法的返回值类型的类类型
            Class returnType = ms[i].getReturnType();
            builder.append(returnType.getName() + " ");
            //得到方法的名称
            builder.append(ms[i].getName() + "(");
            //获取参数类型--->得到的是参数列表的类型的类类型
            Class[] paramTypes = ms[i].getParameterTypes();
            for (Class class1 : paramTypes) {
                builder.append(class1.getName() + ",");
            }
            builder.deleteCharAt(builder.length() - 1).append(")").append("\n");
        }

        Log.e(tag, "printClassMethodMessage: " + builder.toString());
    }

    /**
     * 获取成员变量的信息
     * 成员变量也是对象
     * java.lang.reflect.Field
     * Field类封装了关于成员变量的操作
     * getFields()方法获取的是所有的public的成员变量的信息
     * getDeclaredFields获取的是该类自己声明的成员变量的信息
     */
    public static void printFieldMessage(Class c, String tag) {

        StringBuilder builder = new StringBuilder();

        //Field[] fs = c.getFields();
        Field[] fs = c.getDeclaredFields();
        for (Field field : fs) {
            //得到成员变量的类型的类类型
            Class fieldType = field.getType();
            String typeName = fieldType.getName();
            //得到成员变量的名称
            String fieldName = field.getName();
            builder.append(typeName + ": " + fieldName).append("\n");
        }

        Log.e(tag, "printFieldMessage: " + builder.toString());
    }

    /**
     * 打印对象的构造函数的信息
     * 构造函数也是对象
     * java.lang. Constructor中封装了构造函数的信息
     * getConstructors获取所有的public的构造函数
     * getDeclaredConstructors得到所有的构造函数
     */
    public static void printConMessage(Class c, String tag) {

        StringBuilder builder = new StringBuilder();

        Constructor[] cs = c.getDeclaredConstructors();
        for (Constructor constructor : cs) {
            builder.append(constructor.getName() + "(");
            //获取构造函数的参数列表--->得到的是参数列表的类类型
            Class[] paramTypes = constructor.getParameterTypes();
            for (Class class1 : paramTypes) {
                builder.append(class1.getName() + ",");
            }
            builder.append(")");
        }
        Log.e(tag, "printConMessage: " + builder.toString());
    }

    public static void printAll(Class c, String tag) {

        if (c.getClassLoader() == null) {
            return;
        }
        Log.e(tag, "类的名称是:" + c.getName());
        Log.e(tag, "  ,classLoader:" + c.getClassLoader().getClass().getName());

        printConMessage(c, tag);
        printFieldMessage(c, tag);
        printClassMethodMessage(c, tag);
    }

    public static void printStack() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            Log.e("chong", "statck : " + stackTrace[i].getClassName());
        }
    }
}
