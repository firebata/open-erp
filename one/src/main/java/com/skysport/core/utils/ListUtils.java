package com.skysport.core.utils;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/17.
 */
public enum ListUtils {
    SINGLETONE;

    /**
     * 两个集合的交集
     *
     * @param src1
     * @param src2
     * @return
     */
    public List<String> getIntersection(List<String> src1, List<String> src2) {
        src1.retainAll(src2);
        return src1;
    }

    /**
     * @param src          需要求差集的集合
     * @param intersection 交集
     * @return
     */
    public List<String> getSubtraction(List<String> src, List<String> intersection) {
        src.removeAll(intersection);
        return src;
    }
}
