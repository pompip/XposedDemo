package com.example.bonree.xposeddemo.xpose.HoodImp;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class DumpDex {
    private static final String TAG = "DumpDex";
    private static Method sGetDexMd;
    private static Method sGetBytesMd;

    public static void dump() {
        try {
            if (sGetDexMd == null) {
                Class<?> clazz = Class.forName("java.lang.Class");
                sGetDexMd = clazz.getDeclaredMethod("getDex");
            }
            if (sGetBytesMd == null) {
                Class<?> dexClazz = Class.forName("com.android.dex.Dex");
                sGetBytesMd = dexClazz.getDeclaredMethod("getBytes");
            }
        }catch (Exception e){
            Log.e(TAG, "dumpDex: ",e );
        }
        XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, boolean.class,new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param)throws Throwable {
                super.beforeHookedMethod(param);

            }

            @Override
            protected void afterHookedMethod(MethodHookParam param)throws Throwable {
                super.afterHookedMethod(param);
                Log.e(TAG,"loadClass : " + param.args[0]);
                if (!param.hasThrowable()) {
                    if (checkPn((String) param.args[0])) {
                        dump((Class) param.getResult());
                    }
                }
            }
        });
    }

    private static boolean checkPn(String arg) {
        return arg != null && arg.startsWith("com.cmcc");
    }

    private static void dump(Class arg) {
        if (arg != null) {
            try {
                Object dex = sGetDexMd.invoke(arg);
                Log.e(TAG, "dump: dex = " + dex);
                byte[] bytes = (byte[]) sGetBytesMd.invoke(dex);
                Log.e(TAG,"bytes.length = " + (bytes == null ? 0 : bytes.length));
                if (bytes != null) {
                    File dir = new File("/sdcard/dexDump");
                    if (!dir.isDirectory()) {
                        dir.mkdir();
                    }
                    File file = new File(dir, "dump_" + bytes.length + ".dex");
                    if (!file.exists()) {
                        FileOutputStream out = new FileOutputStream(file);
                        out.write(bytes);
                        out.close();
                        Log.e(TAG, "dump: dex file = " + file.getName());
                    }
                }
            } catch (Exception e) {
                Log.e(TAG,"dump",e);
            }
        }
    }
}
