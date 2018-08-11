package com.example.bonree.xposeddemo.xpose.HoodImp;

import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class YoukuHook {
    private static String className = "com.youku.uplayer.AliMediaPlayer";
    public static boolean hook(String _className,ClassLoader classLoader){
        if (className.equals(_className)){
            Log.e("chong","loadsucess");
            new YoukuHook(classLoader).hookPlayer();
            return true;
        }
        return false;
    }
    private ClassLoader classLoader;

    public YoukuHook(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void hookPlayer(){
        Class<?> aClass = XposedHelpers.findClass(className, classLoader);
        XposedHelpers.findAndHookConstructor(aClass,  new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                for (StackTraceElement stackTraceElement : stackTrace) {
                    XposedBridge.log("chong,className:  "+  stackTraceElement.getClassName() + "");
                  Log.e("chong","className:  "+  stackTraceElement.getClassName() + "");
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
            }
        });
    }

}
