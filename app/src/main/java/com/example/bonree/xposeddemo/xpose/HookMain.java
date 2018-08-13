package com.example.bonree.xposeddemo.xpose;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.util.Log;
import android.view.TextureView;
import android.widget.ImageView;

import com.example.bonree.xposeddemo.xpose.HoodImp.YoukuHook;
import com.example.bonree.xposeddemo.xpose.util.ClassUtil;

import javax.security.auth.login.LoginException;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam)  {
        try {
            if ("com.youku.phone".equals(lpparam.packageName)) {
                XposedHelpers.findAndHookMethod(Instrumentation.class, "callActivityOnResume", Activity.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                        Object[] clazz = param.args;
                        Activity activity = (Activity) clazz[0];
//                        if(currentActivity == null && activity!=null) {
                            //主activity

                            ClassLoader cl = activity.getApplicationContext().getClassLoader();
//                        }

                        XposedHelpers.findAndHookMethod(cl.getClass(), "loadClass", String.class, new XC_MethodHook() {

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.beforeHookedMethod(param);
                                Class<?> result = (Class<?>) param.getResult();
                                if (result == null) return;
                                String className = result.getName();
                            if (className.startsWith("com.ali")){
                                Log.e("chong", "afterHookedMethod: " + className);
                            }

                            }
                        });
                        Class<?> aClass = XposedHelpers.findClass("dalvik.system.DexClassLoader", cl);
                        ClassUtil.printAll(aClass, "chong");


                        XposedBridge.hookAllMethods(aClass,"loadClass", new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            Log.e("chong", "MappingClassLoader before: "+param.args[0] );
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                Object result = param.thisObject;
                                Log.e("chong", "MappingClassLoader after: " + result);

                                Class<?> aClass1 = XposedHelpers.findClass("c8.mdq", result.getClass().getClassLoader());
                                ClassUtil.printAll(aClass1, "chong");
                                ClassUtil.printStack();


                            }
                        });

//                    XposedHelpers.findAndHookMethod("dalvik.system.BaseDexClassLoader",cl, "findClass", String.class, new XC_MethodHook() {
//                        @Override
//                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
//                        }
//
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            if (param.hasThrowable()) return;
//                            if (param.args.length != 1) return;
//
//
//                            Class<?> cls = (Class<?>) param.getResult();
//                            String name = cls.getName();
//                            if (name.startsWith("com.youku.uplayer")){
//                                Log.e("chong", "uplayer: " + name );
//                            }
//
//                        }
//                    });

//
                    }
                });


            }
        }catch (Throwable e){
            e.printStackTrace();
        }

    }
}
