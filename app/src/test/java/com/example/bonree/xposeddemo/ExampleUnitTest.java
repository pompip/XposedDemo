package com.example.bonree.xposeddemo;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

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

    }

    @Test
    public void testScanner(){
        String xxx = "1\n2\n3333\n";
        Scanner scanner = new Scanner(xxx);
        while (scanner.hasNextLine()){
            System.out.println( scanner.nextLine());;
        }
    }
    @Test
    public void  testSub(){
        String blackList ="BlackList=com.haha,com.dd";
       String result =  blackList.substring(blackList.indexOf("=")+1,blackList.length());
      String[] r =  result.split(",");
      List<String> list = Arrays.asList(r);
      for (String s:r){
          System.out.println(s);
      }
        System.out.println(result);

      StringBuilder builder = new StringBuilder();
      for (String s:list){
          builder.append(s).append(",");
      }
      builder.delete(builder.lastIndexOf(","),builder.length());
        System.out.println(builder.toString());
    }

    void AA(){

    }
    @Test
    public void testMathProblem(){
        CountDownLatch latch = new CountDownLatch(2);
        AAA aaa = new AAA();
        BBB bbb = new BBB(aaa,1,latch);
        BBB bbb2 = new BBB(aaa,0,latch);
        new Thread(bbb).start();
        new Thread(bbb2).start();
        try {
            System.out.println("等待2个子线程执行完毕...");
            latch.await();
            System.out.println("2个子线程已经执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        BBB bbb3 = new BBB(aaa,1,cyclicBarrier);
        BBB bbb4 = new BBB(aaa,0,cyclicBarrier);
        new Thread(bbb3).start();
        new Thread(bbb4).start();
        System.out.println("子线程已经执行完毕");
    }

   class AAA{

       synchronized public void methodA(){

            try {
                System.out.println("aaa");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

       synchronized  public void methodB(){
            System.out.println("bbb");
        }
   }

   class BBB implements Runnable{
        AAA aaa;
        int a;
       CountDownLatch latch;
       CyclicBarrier cyclicBarrier;
        public BBB(AAA aaa,int a,CountDownLatch latch){
            this.aaa = aaa;
            this.a = a;
            this.latch = latch;
        }

       public BBB(AAA aaa,int a,CyclicBarrier cyclicBarrier){
           this.aaa = aaa;
           this.a = a;
           this.cyclicBarrier = cyclicBarrier;
       }

       @Override
       public void run() {
            if (a>0){
                aaa.methodA();
            }else {
                aaa.methodB();
            }

            if (latch!=null){
                latch.countDown();
            }
            if (cyclicBarrier!=null){
                try {
                    System.out.println("等待2个子线程执行完毕...:"+a);
                    cyclicBarrier.await();
                    System.out.println("2个子线程已经执行完毕:"+a);

                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }

       }
   }
}