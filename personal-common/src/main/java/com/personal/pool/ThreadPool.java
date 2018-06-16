package com.personal.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 并行执行多个任务
 * @author  daila
 * @date 2017/12/28.
 */
public class ThreadPool {
    private static ThreadPool threadPool;
    private ExecutorService pool;
    private static int threadNum = 10;
    private ThreadPool(){}//禁止创建多个池对象

    public static LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
    /**
     * 获取线程池
     * @return
     */
    public synchronized static ThreadPool getThreadPool() {
        if(threadPool==null){
            threadPool = new ThreadPool();
            threadPool.pool = Executors.newFixedThreadPool(threadNum);
        }
        return threadPool;
    }

    /**
     * 获取线程池
     * @return
     */
    public synchronized static ThreadPool getThreadPool(int threadNum) {
        if(threadPool==null){
            threadPool = new ThreadPool();
            threadPool.pool = Executors.newFixedThreadPool(threadNum);
        }
        return threadPool;
    }

    /**
     * 将任务添加到线程池执行
     * 如果调用了futue.get()方法，会造成主线程阻塞，直到线程池执行完成
     * @param task
     * @return
     */
    public <T> Future<T> addTask(Callable<T> task) {
        return pool.submit(task);
    }
    /**
     * 将任务添加到线程池执行
     * 如果调用了futue.get()方法，会造成主线程阻塞，直到线程池执行完成
     * @param task
     * @return
     */
    public void addTask(Runnable task) {
        pool.execute(task);
    }

    /**
     * 获取执行器
     * @return
     */
    public ExecutorService getPool(){
        return pool;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int a = 1;
        int b = 0;
        for(int i=0;i<100;i++){
            ThreadPool.getThreadPool().addTask(new Runnable() {
                @Override
                public void run() {
                    int b = 0;
                    System.out.println(b);
                    System.out.println(Thread.currentThread());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //count.decrementAndGet();
                }
            });
        }
    }
}
