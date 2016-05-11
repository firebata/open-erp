package com.skysport.inerfaces.model.permission.userinfo.service;

/**
 * 说明:
 * Created by zhangjh on 2016/4/10.
 */
public interface IStaffService {
    /**
     * 获取部门普通人员的角色id
     *
     * @return
     */
    String getCommonStaffGroupId();

    /**
     * 获取部门经理的角色id
     *
     * @return
     */
    String getManagerStaffGroupId();

    /**
     * 获取父角色id
     *
     * @param groupId
     * @return
     */
    String getParentGroupId(String groupId);

    String staffId();

    String managerId();

    public String getStaffGroupId();
}
