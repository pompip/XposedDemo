package com.example.bonree.xposeddemo.xpose;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if ("com.example.bonree.myapplication".equals(lpparam.packageName)){
            Class loadClass = lpparam.classLoader.loadClass("com.example.bonree.myapplication.MainActivity");
            XposedHelpers.findAndHookMethod(loadClass, "getText", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XposedBridge.log("已经Hook");
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult("你已经被劫持,哈哈哈哈...");
                }
            });
        }
    }
}
