package com.skysport.core.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.skysport.core.bean.SpringContextHolder;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;

/**
 * 说明:
 * Created by zhangjh on 2015/8/18.
 */
public class UuidGeneratorUtils {
    private static TimeBasedGenerator gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());

    public static String getUUID() {
        return gen.generate().toString();
    }


    public static String getNextId() {
        StrongUuidGenerator strongUuidGenerator = SpringContextHolder.getBean("uuidGenerator");
        return strongUuidGenerator.getNextId();
    }

}
