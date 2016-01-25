package com.skysport.core.mapper;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/12/25.
 */
@Component("idapMapper")
public interface IdapMapper {

    List<String> findAllDeveloper();

    List<String> findAllOPer();

    List<String> findManagerOfDevDept();

    List<String> findManagerOfOP();
}
