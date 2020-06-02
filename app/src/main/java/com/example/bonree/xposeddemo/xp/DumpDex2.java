package com.example.bonree.xposeddemo.xp;


import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

import dalvik.system.DexClassLoader;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class DumpDex2 {
    private static final String TAG = "DumpDex";

    public static void dump() {
    
            Log.e(TAG, "Loading " );
            try {
				initZygote();
			} catch (Throwable e) {
				e.printStackTrace();
			}

            final ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
            final ClassLoader bootLoader = DexClassLoader.class.getClassLoader();

            XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    Log.e(TAG, "loadClass: "+ Arrays.toString(param.args) );
                    ClassLoader loader = (ClassLoader) param.thisObject;
//                    if (param.hasThrowable()
//                            || loader == bootLoader
//                            || loader == systemLoader
//                            || loader.getParent() == systemLoader) {
//                        return;
//                    }

                    Class<?> clazz = (Class<?>) param.getResult();
                    if(clazz==null) {
                    	return;
                    }
                    String clazzName = clazz.getName();

                    if(!clazzName.contains("tv.danmaku.ijk")) {
                    	return;
                    }
                    Log.e(TAG, "Loading :"+clazzName+" loader:"+loader );
                    Object dex = XposedHelpers.callMethod(clazz, "getDex");
                    byte[] data = (byte[]) XposedHelpers.callMethod(dex, "getBytes");

                    File file = new File(DUMP_PATH, data.length + ".dex");
                    if (!file.exists() && file.createNewFile()) {
                        file.setReadable(true, false);
                        new Thread(new IO(data, file)).start();
                    }
                }
            });
    }
    private static final File DUMP_PATH = new File("/sdcard/dumpdex2/");
  
    public static void initZygote( ) {
        if (DUMP_PATH.exists()) return;
        DUMP_PATH.mkdirs();
        DUMP_PATH.setReadable(true, false);
        DUMP_PATH.setWritable(true, false);
        DUMP_PATH.setExecutable(true, false);
    }
    
    static class IO implements Runnable {

        private final byte[] data;
        private final File file;

        private IO(byte[] data, File file) {
            this.data = data;
            this.file = file;
        }

        @Override
        public void run() {
            Log.i(TAG, "dump dex => " + file);
            try  {
            	BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
                output.write(data);
            } catch (Exception e) {
                Log.e(TAG, "Dump Dex Exception", e);
            }
        }
    }
}
