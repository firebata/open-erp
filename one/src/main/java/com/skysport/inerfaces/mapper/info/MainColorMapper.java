package com.skysport.inerfaces.mapper.info;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/15.
 */
@Repository("mainColorMapper")
public interface MainColorMapper<T> {
    void add(List<T> mainColorList);

    void delete(String natrualkey);
}
