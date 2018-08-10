package com.example.bonree.xposeddemo.xpose;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if ("com.example.bonree.myapplication".equals(lpparam.packageName)) {
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
        } else
//            if ("com.ss.android.article.news".equals(lpparam.packageName))
        {

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
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
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
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
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

            Class mediaClass = XposedHelpers.findClass("android.media.MediaPlayer", lpparam.classLoader);
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


            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                  final   ClassLoader cl = ((Context) param.args[0]).getClassLoader();


                    Log.i("jyy", "寻找xxx.xxx.xxx成功");

                    try {


                    Class controllerClass = XposedHelpers.findClass("com.ss.android.detail.feature.detail2.video.d", cl);

                    XposedHelpers.findAndHookMethod(controllerClass, "getVideoController", new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            XposedBridge.log("hook xxx after");
                            Object result = param.getResult();
                            XposedBridge.log(result.toString());

                            try {



                            Class control = Class.forName("com.ss.android.video.core.playersdk.d.a");
                            // 动态代理 INotificationManager

                            Method getVideoId = control.getMethod("getVideoId");
                            Object invoke = getVideoId.invoke(result);

                                XposedBridge.log(invoke.toString());
                            }catch (Exception e){
                                XposedBridge.log("some Exception 0"+e.getMessage());
                            }

                        }
                    });
                    }catch (Exception e){
                        XposedBridge.log("some Exception 1"+ e.getMessage());
                    }

                    Class playerClass = XposedHelpers.findClass("com.ss.android.video.core.playersdk.d.a",cl);
                    Class paramClass = XposedHelpers.findClass("com.bytedance.article.common.model.detail.a",cl);

                    XposedHelpers.findAndHookMethod(playerClass, "setPlayArticle",paramClass, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);

                            XposedBridge.log("hook controller xxx Player");
                            Object arg =  param.args[0];

                            XposedBridge.log(arg.toString());
                        }
                    });
                }
            });
        }
    }
}
