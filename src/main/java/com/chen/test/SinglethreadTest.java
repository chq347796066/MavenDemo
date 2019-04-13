package com.chen.test;

import java.util.List;

public class SinglethreadTest {
    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();
        int page=0;
        int size=1000;
        while (true){
            List<Data> list=Pallets.getPallets(page*size,size);
            write(list);
            if(list.size()<size){
                break;
            }
            page++;
        }
        long endTime=System.currentTimeMillis();
        System.out.println("total time:"+(endTime-startTime)/1000);

    }

    public static void write(List<Data>list)  {
        for(Data data:list){
            System.out.println("data:"+data);
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
