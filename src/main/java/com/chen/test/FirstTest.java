package com.chen.test;

import sun.jvmstat.perfdata.monitor.PerfStringVariableMonitor;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class FirstTest {
    static ExecutorService executor= Executors.newFixedThreadPool(10);

    private static int page=0;
    private static final int size=1000;
    private static int THREAD_SIZE=5;
    private static Queue<Data>queue=new ArrayBlockingQueue<Data>(10000);
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
                    queue.addAll(list);
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
                Data data = queue.poll();
                if(data!=null){
                    System.out.println("data:"+data);
                }
                if(atomicBoolean.get()){
                    break;
                }
            }
        }
    }


}
