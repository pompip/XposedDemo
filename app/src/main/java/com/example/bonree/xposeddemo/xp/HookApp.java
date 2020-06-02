package com.example.bonree.xposeddemo.xp;


import android.app.Application;
import android.app.Instrumentation;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookApp  implements IXposedHookLoadPackage {
    private static final String TAG = "HookApp";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        Log.e(TAG, "handleLoadPackage111: chong" );
//        DumpDex2.dump();
        XposedHelpers.findAndHookMethod(Instrumentation.class, "callApplicationOnCreate", Application.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Application app = (Application) param.args[0];
                KGZBPlayerWrapper.getInstance().hook(app.getClassLoader());
            }
        });


    }
}
