package com.example.bonree.xposeddemo.xpose;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.util.Log;

import com.example.bonree.xposeddemo.xpose.HoodImp.YoukuHook;
import com.example.bonree.xposeddemo.xpose.util.ClassUtil;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        hookActivity(lpparam);
        hookBaseDexClassLoader(lpparam);
    }

    private static void hookBaseDexClassLoader(final XC_LoadPackage.LoadPackageParam lpparam) {
        Constructor<?>[] csts = BaseDexClassLoader.class.getConstructors();
        for (Constructor<?> cst : csts) {
            XposedBridge.hookMethod(cst, new XC_MethodHook() {
                protected void afterHookedMethod(MethodHookParam param) {
                    try {
                        String dexPath = (String) param.args[0];
                        File file = (File) param.args[1];
                        String fileName = "null";
                        if (file != null) {
                            fileName = file.getAbsolutePath();
                        }
                        String librarySearchPath = (String) param.args[2];
                        ClassLoader classLoaderParent = (ClassLoader) param.args[3];
                        String classLoadName = "null";
                        if (classLoaderParent != null) {
                            classLoadName = classLoaderParent.getClass().getName();
                        }

                        ClassLoader classLoader = (ClassLoader) param.thisObject;
                        Log.e("chong_main", "dexPath:" + dexPath
                                + ",file:" + fileName
//                                + ",librarySearchPath:" + librarySearchPath
                                + ",classLoaderParent:" + classLoadName);
                        hookMain((ClassLoader) (classLoader), lpparam.packageName);
                    } catch (Exception e) {
                        Log.e("chong", "classLoad Hook Error ", e);
                    }
                }
            });
        }
    }

    private static void hookActivity(final XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod(Instrumentation.class, "callActivityOnResume", Activity.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Activity activity = (Activity) param.args[0];
                ClassLoader cl = activity.getApplicationContext().getClassLoader();
                Log.e("chong_main", "classLoad:" + cl.getClass().getName());
                hookMain(cl, lpparam.packageName);
            }
        });
    }

    private static void hookMain(ClassLoader classLoader, String packageName) {
        if ("com.youku.phone".equals(packageName)) {
            YoukuHook.getInstance().hook(classLoader);
        }
    }

}
