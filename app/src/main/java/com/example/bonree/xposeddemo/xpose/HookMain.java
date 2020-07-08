package com.example.bonree.xposeddemo.xpose;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Arrays;

import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {
    private static final String TAG = "chong";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
        Log.e(TAG, "handleLoadPackage start :" + lpparam.packageName);
        if (lpparam.packageName.equals("com.laowang7") || lpparam.packageName.equals("com.qibajiu")) {
            XposedHelpers.findAndHookMethod(Instrumentation.class, "callApplicationOnCreate", Application.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    Application app = (Application) param.args[0];
                    ClassLoader cl = app.getClassLoader();
                    Log.e(TAG, "callApplicationOnCreate:" + Arrays.toString(param.args));
                    try {
                        load(app, lpparam.getClass().getClassLoader());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });


        }
        XposedBridge.hookAllMethods(
                android.os.Process.class,
                "start",
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        final String pacakgeName = (String) param.args[1];
                        final int uid = (int) param.args[2];
                        final int runtimeFlags = (int) param.args[5];

//                            final int user = UserHandle.getUserHandleForUid(uid).hashCode();
                        if (pacakgeName.equals("com.laowang7") || pacakgeName.equals("com.qibajiu")) {
                            param.args[5] = runtimeFlags | 1;
                        }
                    }

                }
        );
        Log.e(TAG, "handleLoadPackage end");


    }


    private void load(Context context, ClassLoader classLoader) throws Exception {
        Log.e(TAG, "load start");
        String packageCodePath = context.getPackageCodePath();
        Log.e(TAG, "getPackageCodePath1:" + packageCodePath);
        PackageManager packageManager = context.getPackageManager();

        ApplicationInfo packageInfo = packageManager.getApplicationInfo("com.example.bonree.xposeddemo", 0);

        String sourceDir = packageInfo.sourceDir;
        Log.e(TAG, "getPackageCodePath2:" + sourceDir);

        PathClassLoader commPlugin = new PathClassLoader(sourceDir, classLoader);
        Class<?> HookMainClass = commPlugin.loadClass("com.example.bonree.xposeddemo.xpose.HoodImp.LaowangHook");

        HookMainClass.getMethod("hookMain", ClassLoader.class).invoke(HookMainClass.newInstance(), context.getClassLoader());
        Log.e(TAG, "load end");

    }


}
