package com.malin.executors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * http://www.trinea.cn/android/java-android-thread-pool/
 * http://blog.csdn.net/sd0902/article/details/8395677
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ThreadPool";
    private String mThreadName;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.tv_name);

//        newSingleThreadExecutor();
//        newCachedThreadPool();
//        newFixedThreadPool();
        newScheduledThreadPoolTwo();

    }


    private void show0() {
        Thread t1 = new MyThread("one");
        Thread t2 = new MyThread("two");
        Thread t3 = new MyThread("three");
        Thread t4 = new MyThread("four");
        Thread t5 = new MyThread("five");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }

    /**
     *
     */
    private void newSingleThreadExecutor() {

        /**
         one   pool-2-thread-1
         two   pool-2-thread-1
         three pool-2-thread-1
         four  pool-2-thread-1
         five  pool-2-thread-1
         */
        //创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();
        //创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = new MyThread("one");
        Thread t2 = new MyThread("two");
        Thread t3 = new MyThread("three");
        Thread t4 = new MyThread("four");
        Thread t5 = new MyThread("five");
        //将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);

        //关闭线程池
        pool.shutdown();
    }

    private void newCachedThreadPool() {

        /**
         创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，
         那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。
         此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。

         one   pool-3-thread-1
         two   pool-3-thread-2
         three pool-3-thread-3
         four  pool-3-thread-4
         five  pool-3-thread-1

         当执行第五个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
         */
        //创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        //创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = new MyThread("one");
        Thread t2 = new MyThread("two");
        Thread t3 = new MyThread("three");
        Thread t4 = new MyThread("four");
        Thread t5 = new MyThread("five");
        //将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);

        //关闭线程池
        pool.shutdown();
    }

    private void newFixedThreadPool() {

        /**
         创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。

         one   pool-2-thread-1
         two   pool-2-thread-2
         three pool-2-thread-1
         four  pool-2-thread-2
         five  pool-2-thread-1
         */
        //创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);
        //创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = new MyThread("one");
        Thread t2 = new MyThread("two");
        Thread t3 = new MyThread("three");
        Thread t4 = new MyThread("four");
        Thread t5 = new MyThread("five");
        //将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);

        //关闭线程池
        pool.shutdown();
    }


    private void newScheduledThreadPoolOne() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(new MyRunnable("one"), 2000, TimeUnit.MILLISECONDS);
        scheduledThreadPool.schedule(new MyRunnable("two"), 2000, TimeUnit.MILLISECONDS);
        scheduledThreadPool.schedule(new MyRunnable("three"), 2000, TimeUnit.MILLISECONDS);
    }

    private void newScheduledThreadPoolTwo() {
        //表示延迟1秒后每3秒执行一次。
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(new MyRunnable("one"), 1000,3000, TimeUnit.MILLISECONDS);
    }


    class MyRunnable implements Runnable {

        private String name;

        private MyRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, name);
                    Log.d(TAG,""+System.currentTimeMillis());
                    Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class MyThread extends Thread {
        private String name;

        private MyThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mThreadName = Thread.currentThread().getName();
            Log.d(TAG, name + " " + mThreadName);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mTextView.setText(name + " " + mThreadName);
                }
            });
        }
    }
}
