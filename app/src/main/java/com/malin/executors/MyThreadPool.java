package com.malin.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by malin on 16-5-10.
 */
public class MyThreadPool {

    /**
     * Android中单线程可用于数据库操作，文件操作，应用批量安装，
     * 应用批量删除等不适合并发但可能IO阻塞性及影响UI线程响应的操作。
     * @param args
     */
    public static void main(String[] args) {
        //创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();
        //创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        Thread t4 = new MyThread();
        Thread t5 = new MyThread();
        //将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);
        //关闭线程池
        pool.shutdown();
    }

    static class MyThread extends Thread{
        @Override
        public void run() {
            super.run();

        }
    }
}
