package com.example.bonree.xposeddemo.xpose;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.bonree.xposeddemo.xpose.HoodImp.YoukuHook;
import com.example.bonree.xposeddemo.xpose.util.ClassUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import dalvik.system.BaseDexClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        Log.e("chong", "andleLoadPackage: chong");
        XposedHelpers.setStaticObjectField(Build.class, "BOARD", new String("HTC"));
        hookActivity(lpparam);

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
        XposedHelpers.findAndHookMethod(Instrumentation.class, "callActivityOnCreate", Activity.class, Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                Activity activity = (Activity) param.args[0];
                ClassLoader cl = activity.getApplicationContext().getClassLoader();
                Log.e("chong_main", "classLoad:" + cl.getClass().getName());

            }
        });
        XposedHelpers.findAndHookMethod(Instrumentation.class, "callApplicationOnCreate", Application.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                Application app = (Application) param.args[0];
                ClassLoader cl = app.getClassLoader();
                Log.e("chong_main", "callApplicationOnCreate:" + cl.getClass().getName());

                try {
                    hookMain(cl, lpparam.packageName);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private static void hookMain(final ClassLoader classLoader, String packageName) throws Exception {
        if (packageName.equals("com.laowang7")) {
            Log.e(TAG, "hookMain: start Hook baseApplication" );
            Class<?> baseApplicationClasss = classLoader.loadClass("com.core.base.BaseApplication");
            XposedHelpers.findAndHookMethod(baseApplicationClasss, "getDeviceUuid", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Class<?> stringUtilClass = classLoader.loadClass("com.core.util.StringUtil");
                    Log.e(TAG, "hookMain: "+stringUtilClass.toString() );
                    String uuidString = (String) XposedHelpers.callStaticMethod(stringUtilClass,"getMd5Of",UUID.randomUUID().toString());
                    Log.e(TAG, "hookMain: "+uuidString );
                    param.setResult(uuidString);
                }
            });
            Log.e(TAG, "hookMain: mid Hook baseApplication" );
            XposedHelpers.findAndHookMethod(baseApplicationClasss, "getOldDeviceUuid", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Class<?> stringUtilClass = classLoader.loadClass("com.core.util.StringUtil");
                    Log.e(TAG, "hookMain: getOldDeviceUuid"+stringUtilClass.toString() );
                    String uuidString = (String) XposedHelpers.callStaticMethod(stringUtilClass,"getMd5Of",UUID.randomUUID().toString());
                    Log.e(TAG, "hookMain: getOldDeviceUuid"+uuidString );
                    param.setResult(uuidString);
                }
            });

            Log.e(TAG, "hookMain: end Hook baseApplication" );
        }
    }

    private String md5(String str){
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                byte b2 = (byte)( b & 255);
                if (b2 < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(b2));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e3) {
            e3.printStackTrace();
            return null;
        }

    }

    private static final String TAG = "chong";

}
