package com.skysport.interfaces.model.relation;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/15.
 */
public interface IRelationIdDealService<T> {
    void backupRecordsToHis(List<T> vos);

    void batchInsert(List<T> vos);

    List<String> queryProjectChildIdsByParentId(String projectId);
}
