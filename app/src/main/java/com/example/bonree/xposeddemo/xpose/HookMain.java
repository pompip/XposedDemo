package com.example.bonree.xposeddemo.xpose;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.bonree.xposeddemo.xpose.HoodImp.YoukuHook;
import com.example.bonree.xposeddemo.xpose.util.ClassUtil;

import javax.security.auth.login.LoginException;

import dalvik.system.DexClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if ("com.youku.phone".equals(lpparam.packageName)) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ClassLoader cl = ((Context) param.args[0]).getClassLoader();


//                    XposedHelpers.findAndHookMethod(cl.getClass(), "loadClass", String.class, new XC_MethodHook() {
//
//
//
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            super.beforeHookedMethod(param);
//                            Class<?> result = (Class<?>) param.getResult();
//                            if (result==null)return;
//                            String className = result.getName();
//                            if (className.startsWith("com.youku")){
//                                Log.e("chong", "afterHookedMethod: " +className );
//                            }
//
//                        }
//                    });
                     try {
                        Class<?> mdqClass = XposedHelpers.findClass("com.youku.uplayer.AliMediaPlayer", cl);

                         ClassUtil.printAll(mdqClass,"chong");
                    }catch (Exception e){
                        e.printStackTrace();
                    }

//
                }
            });

        }


    }
}
