package com.chen.test;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class Data {
    private String id;
    private long index;
    private String poNbr;
    private String poTime;
}
