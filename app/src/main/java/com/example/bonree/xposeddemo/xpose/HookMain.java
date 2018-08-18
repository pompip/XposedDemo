package com.example.bonree.xposeddemo.xpose;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;

import com.example.bonree.xposeddemo.xpose.util.ClassUtil;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {

        if ("com.youku.phone".equals(lpparam.packageName)) {
            XposedHelpers.findAndHookMethod(Instrumentation.class, "callActivityOnResume", Activity.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Activity activity = (Activity) param.args[0];
                    ClassLoader cl = activity.getApplicationContext().getClassLoader();


                    Class<?> aClass = XposedHelpers.findClass("com.youku.uplayer.AliMediaPlayer", cl);
                    XposedBridge.hookAllConstructors(aClass, new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            Object result = param.thisObject;

                            ClassUtil.printAll(result.getClass(), "chong");

                            Field[] declaredFields = result.getClass().getDeclaredFields();
                            for (Field field : declaredFields) {
                                ClassUtil.printAll(field.getType(), "chong");
                            }

                        }
                    });


                    Class<?> listenerClass = XposedHelpers.findClass("com.youku.uplayer.OnInfoListener", cl);

                    XposedHelpers.findAndHookMethod(listenerClass, "onInfo", int.class, int.class, int.class, Object.class, long.class
                            , new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                    Object o = param.args[3];
                                    if (o != null) {
                                        ClassUtil.printAll(o.getClass(), "chong   oninfo");
                                    }
                                }
                            });


//
                }
            });


        }


    }
}
