package com.example.bonree.xposeddemo;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testRuntime() throws IOException, InterruptedException {

        Process exec = Runtime.getRuntime().exec("adb shell screencap -p /sdcard/screen.png&adb pull /sdcard/screen.png D:/test/ ");
        exec.waitFor();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }


    }


    @Test
    public  void main1() throws Exception {
        Process exec1 = Runtime.getRuntime().exec("F:/android/SDK/platform-tools/adb shell screencap -p /sdcard/screen.png");
        exec1.waitFor();
        Process exec2 = Runtime.getRuntime().exec("F:/android/SDK/platform-tools/adb pull /sdcard/screen.png D:/test/");
        exec2.waitFor();


//        process.waitFor();
//        process.isAlive()
//        //读取屏幕输出
//
//
//        BufferedReader strCon = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        String line;
//        while ((line = strCon.readLine()) != null) {
//            System.out.println(line);
//        }
//
//        BufferedReader strCon1 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//        String line1;
//        while ((line1 = strCon1.readLine()) != null) {
//            System.out.println(line1);
//        }
//
//        process.destroy();
    }
}