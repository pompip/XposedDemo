package com.example.bonree.xposeddemo;

import android.app.Instrumentation;
import android.arch.core.BuildConfig;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import dalvik.system.DexClassLoader;

//import com.google.android.exoplayer2.ui.PlayerView;

public class MainActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = new Intent();
//        intent.putExtra("",new Bundle());
//        intent.getBundleExtra();
//        sendBroadcast(intent);
        setContentView(R.layout.activity_main);

//        PlayerView view = findViewById(R.id.player_view);
//        Timer timer =  new Timer();
//        timer.cancel();
//        timer.purge();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//        },1000);




    }
    void test(){

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

