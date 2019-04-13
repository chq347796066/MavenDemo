package com.chen.test;

import sun.jvmstat.perfdata.monitor.PerfStringVariableMonitor;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MultithreadingTest {
    static ExecutorService executor= Executors.newFixedThreadPool(10);

    private static int page=0;
    private static final int size=1000;
    private static int THREAD_SIZE=5;
    private static Queue<List<Data>>queue=new ArrayBlockingQueue<List<Data>>(100);
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        for(int i=0;i<THREAD_SIZE;i++){
            executor.submit(new GetDataTask());
        }
        for(int i=0;i<THREAD_SIZE;i++){
            executor.submit(new WriteDataTask());
        }
        executor.shutdown();
        while (true){
            if(executor.isTerminated()){
                System.out.println("done");
                break;
            }
        }
        long end=System.currentTimeMillis();
        System.out.println("total time:"+(end-start)/1000);


    }


    static volatile AtomicBoolean atomicBoolean=new AtomicBoolean(false);

    static class GetDataTask implements Runnable{
        public void run() {
            while (!atomicBoolean.get()) {
                List<Data>list=null;
                synchronized (GetDataTask.class){
                    list = Pallets.getPallets(page * size, size);
                    page++;
                }
                if(list!=null&&list.size()>0) {
                    queue.add(list);
                }
                if (list.size() < size) {
                    System.out.println("GetDataTask done");
                    atomicBoolean.set(true);
                }

            }
        }
    }

    static class WriteDataTask implements Runnable{
        public void run() {
            while (true){
                List<Data>list= queue.poll();
                if(list!=null&&list.size()>0){
                    for (Data data:list){
                        System.out.println("data:"+data);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(atomicBoolean.get()){
                    break;
                }
            }
        }
    }


}
