package com.example.bonree.xposeddemo.xpose.HoodImp;

import android.util.Log;

import com.example.bonree.xposeddemo.xpose.util.ClassUtil;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class YoukuHook {
    private YoukuHook() {
    }

    static class Inner {
        static YoukuHook hook = new YoukuHook();
    }

    public static YoukuHook getInstance() {
        return Inner.hook;
    }

    public void hook(ClassLoader classLoader) {
        Log.e("chong", "loadsucess");
        hookPlayer(classLoader);
        hookYouku(classLoader);

    }

    private void hookPlayer(ClassLoader classLoader) {
        Class<?> aClass = XposedHelpers.findClass("com.youku.uplayer.AliMediaPlayer", classLoader);
        Class<?> listenerClass = XposedHelpers.findClass("com.youku.uplayer.OnInfoListener", classLoader);

        XposedHelpers.findAndHookMethod(aClass,"setOnInfoListener",listenerClass, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedHelpers.findAndHookMethod(param.args[0].getClass(), "onInfo", int.class, int.class, int.class, Object.class, long.class
                        , new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e("chong_onInfo","1:"+param.args[0]+"2:"+param.args[1]+"3:"+param.args[2]+"5:"+param.args[4]);
                                Object o = param.args[3];
                                if (o != null) {
                                    ClassUtil.printAll(o.getClass(), "chong   oninfo");
                                }
                            }
                        });
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });
    }

    private void hookYouku(ClassLoader classLoader) {
        Class<?> aClass = XposedHelpers.findClass("com.youku.uplayer.AliMediaPlayer", classLoader);
        XposedBridge.hookAllConstructors(aClass, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Object result = param.thisObject;
                ClassUtil.printAll(result.getClass(), "chong");
                Field[] declaredFields = result.getClass().getDeclaredFields();
                for (Field field : declaredFields) {
                    ClassUtil.printAll(field.getType(), "chong");
                }
            }
        });


    }

}
