package com.example.bonree.xposeddemo.xpose.HoodImp;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class ArticleNewsHook {

    public void hookMediaPlayer(ClassLoader classLoader){

        Class mediaClass = XposedHelpers.findClass("android.media.MediaPlayer", classLoader);
        XposedHelpers.findAndHookMethod(mediaClass, "setDataSource", Context.class, Uri.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);

                        XposedBridge.log("hook android Player");
                        Uri arg = (Uri) param.args[1];

                        XposedBridge.log(arg.toString());
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });
    }

    public void hookIjkPlayer(ClassLoader classLoader){
        Class playClass = XposedHelpers.findClass("tv.danmaku.ijk.media.player.IjkMediaPlayer", classLoader);
        XposedHelpers.findAndHookMethod(playClass, "setDataSource", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedBridge.log("hook Player");
                String arg = (String) param.args[0];
                XposedBridge.log(arg);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

            }
        });
    }

    public void hookMethod(XC_LoadPackage.LoadPackageParam lpparam) {

        Log.e("Hook", "Hook 今日头条");
        XposedBridge.log("Hook 今日头条");
        Class aClass = XposedHelpers.findClass("android.app.NotificationManager", lpparam.classLoader);
        XposedHelpers.findAndHookMethod(aClass,
                "notify",
                String.class,
                int.class,
                Notification.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
//                            param.setResult(null); //把honk到的值设为空（param是hook的方法中的参数的值）

                        XposedBridge.log("hook 通知 before");
                        Notification a = (Notification) param.args[2];

                        XposedBridge.log("loadpackage param" + param.toString());

                        XposedBridge.log("loadpackage AlipayGphone  tickerText" + a.tickerText);

                        XposedBridge.log("loadpackage AlipayGphone  title" + a.extras.get("android.title"));

                        XposedBridge.log("loadpackage AlipayGphone  text" + a.extras.get("android.text"));

                    }

                    @Override
                    protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        XposedBridge.log("hook 通知 after");
                    }
                });

        if ("com.ss.android.article.news".equals(lpparam.packageName)) {
            Class playClass = XposedHelpers.findClass("tv.danmaku.ijk.media.player.IjkMediaPlayer", lpparam.classLoader);
            XposedHelpers.findAndHookMethod(playClass, "setDataSource", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XposedBridge.log("hook Player");
                    String arg = (String) param.args[0];
                    XposedBridge.log(arg);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                }
            });

        }
    }
}
