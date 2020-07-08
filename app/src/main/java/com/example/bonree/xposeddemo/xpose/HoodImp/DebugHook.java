package com.example.bonree.xposeddemo.xpose.HoodImp;

import com.example.bonree.xposeddemo.BuildConfig;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;

public class DebugHook implements IXposedHookZygoteInit ,IXposedHookInitPackageResources {
    private static final int DEBUG_ENABLE_DEBUGGER = 0x1;
    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {

        XposedBridge.hookAllMethods(Process.class, "start", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param)
                    throws Throwable {

                XposedBridge.log("-- beforeHookedMethod :" + param.args[1]);

                int id = 5;
                int flags = (Integer) param.args[id];
                // 修改类android.os.Process的start函数的第6个传入参数
                if ((flags & DEBUG_ENABLE_DEBUGGER) == 0) {
                    // 增加开启Android调试选项的标志
                    flags |= DEBUG_ENABLE_DEBUGGER;
                }
                param.args[id] = flags;

                if (BuildConfig.DEBUG) {
                    XposedBridge.log("-- app debugable flags to 1 :" + param.args[1]);
                }
            }

        });
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {


    }
}
