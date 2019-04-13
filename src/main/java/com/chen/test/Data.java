package com.chen.test;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Data {
    private String id;
    private long index;
    private String poNbr;
    private String poTime;
}
