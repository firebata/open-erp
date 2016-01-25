package com.skysport.core.model.seqno.service;

/**
 * Created by zhangjh on 2015/6/1.
 */
public interface  IncrementNumber {
     int nextVal(String name);

     int reset();

    String nextVal(String kind_name, int length, String currentSeqNo);
}

