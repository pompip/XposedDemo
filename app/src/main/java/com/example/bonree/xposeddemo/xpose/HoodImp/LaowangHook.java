package com.example.bonree.xposeddemo.xpose.HoodImp;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class LaowangHook {
    private static final String TAG = "chong";

    public void hookMain(ClassLoader classLoader) {

        try {


            Log.e(TAG, "hookMain: start Hook baseApplication3");
            Class<?> baseApplicationClasss = classLoader.loadClass("com.core.base.BaseApplication");
            Log.e(TAG, "hookMain: start Hook baseApplication4");
            final Class<?> stringUtilClass = classLoader.loadClass("com.core.util.StringUtil");
            XposedHelpers.findAndHookMethod(baseApplicationClasss, "getDeviceUuid", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Log.e(TAG, "hookMain: " + stringUtilClass.toString());
                    String uuidString = (String) XposedHelpers.callStaticMethod(stringUtilClass, "getMd5Of", UUID.randomUUID().toString());
                    Log.e(TAG, "hookMain: " + uuidString);
                    param.setResult(uuidString);
                }
            });

            XposedHelpers.findAndHookMethod(baseApplicationClasss, "getOldDeviceUuid", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {


                    Log.e(TAG, "hookMain: getOldDeviceUuid" + stringUtilClass.toString());
                    String uuidString = (String) XposedHelpers.callStaticMethod(stringUtilClass, "getMd5Of", UUID.randomUUID().toString());
                    Log.e(TAG, "hookMain: getOldDeviceUuid" + uuidString);
                    param.setResult(uuidString);
                }
            });

            XposedHelpers.findAndHookMethod(baseApplicationClasss, "isApkInDebug", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {


                    Log.e(TAG, "hookMain: isApkInDebug" );
                    param.setResult(true);

                }
            });

//            Class<?> SharedUtilClass = XposedHelpers.findClass("com.core.util.SharedPreferencesUtil", classLoader);
//            XposedBridge.hookAllMethods(SharedUtilClass, "setString", new XC_MethodReplacement() {
//
//                @Override
//                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
//                    Log.e(TAG, "setString:" + Arrays.toString(param.args));
//                    return true;
//                }
//            });

            Log.e(TAG, "hookMain: end Hook baseApplication");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private String md5(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                byte b2 = (byte) (b & 255);
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
}
