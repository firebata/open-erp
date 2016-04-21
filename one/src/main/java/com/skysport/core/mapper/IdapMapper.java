package com.skysport.core.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/12/25.
 */
@Repository("idapMapper")
public interface IdapMapper {

    List<String> findAllDeveloper();

    List<String> findAllOPer();

    List<String> findManagerOfDevDept();

    List<String> findManagerOfOP();
}
