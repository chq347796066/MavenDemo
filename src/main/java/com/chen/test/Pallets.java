package com.chen.test;

import javafx.scene.control.Toggle;

import java.util.ArrayList;
import java.util.List;

public class Pallets {
    private static List<Data>pallets=new ArrayList<Data>();
    public static long TOTAL_SIZE=50000;
    static {
        initData();
    }

    private static void initData() {
        for(long i=0;i<TOTAL_SIZE;i++){
            Data data=new Data(i+"",i,i+"","2019-04-13");
            pallets.add(data);
        }
    }

    public static List<Data>getPallets(int start,int size){
        List<Data>datas=new ArrayList<Data>();
        if(start+size>=pallets.size()){
            for(int i=start;i<pallets.size();i++){
                datas.add(pallets.get(i));
            }
        }else {
            for (int i=start;size>0;size--){
                datas.add(pallets.get(i++));
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return datas;

    }
}
