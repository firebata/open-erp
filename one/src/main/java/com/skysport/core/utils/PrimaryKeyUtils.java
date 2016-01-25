package com.skysport.core.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * 说明:
 * Created by zhangjh on 2015/8/18.
 */
public class PrimaryKeyUtils {
    private static TimeBasedGenerator gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());

    public static String getUUID() {
        return gen.generate().toString();
    }


}
