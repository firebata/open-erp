package com.skysport.inerfaces.mapper.info;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/15.
 */
@Component("mainColorManageMapper")
public interface MainColorManageMapper<T> {
    List<T> queryMainColorList(String projectId);

    void add(List<T> mainColorList);

    void delete(String natrualkey);
}
