package com.chen.test;


import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MultithreadingTest {
    static ExecutorService executor= Executors.newFixedThreadPool(10);

    private static final int size=1000;
    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        int count = 50000;
        for(int i=0;i< (count - 1)/1000 + 1;i++){
            executor.submit(new GetDataTask(i));
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
        private int page;

        public GetDataTask(int page) {
            this.page = page;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public void run() {
                List<Data>list = Pallets.getPallets(page * size, size);
                loadData(list);

            }

        private void loadData(List<Data> list) {
            for(Data data:list){
                if(data.getIndex()==49999){
                    System.out.println(data);
                    System.err.println("get 49999");
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




}
