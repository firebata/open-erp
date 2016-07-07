package com.skysport.core.bean.permission;

import com.skysport.core.bean.system.SelectItem;
import com.skysport.interfaces.bean.common.UploadFileInfo;

import java.util.List;
import java.util.Map;

/**
 * 说明:用户信息
 * Created by zhangjh on 2015/8/17.
 */
public class UserInfo extends SelectItem {

    private String id;

    private String username;

    private String aliases;

    private String profileAddress;

    private String password;

    private String userType;

    private String userEmail;

    private String userMobile;

    private String lockFlag;

    private String question;

    private String answer;

    private String lastLoginTime;

    private String isOnline;

    private String isLimit;

    private int delFlag;

    private String cooperationTime;

    private String remark;

    private String updateTime;

    private int isAdmin;

    private List<UploadFileInfo> fileInfos;
    /**
     * 存放初始化文件上传控件中的信息
     */
    private Map<String, Object> fileinfosMap;

    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public String getProfileAddress() {
        return profileAddress;
    }

    public void setProfileAddress(String profileAddress) {
        this.profileAddress = profileAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(String isLimit) {
        this.isLimit = isLimit;
    }

    @Override
    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getCooperationTime() {
        return cooperationTime;
    }

    public void setCooperationTime(String cooperationTime) {
        this.cooperationTime = cooperationTime;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<UploadFileInfo> getFileInfos() {
        return fileInfos;
    }

    public void setFileInfos(List<UploadFileInfo> fileInfos) {
        this.fileInfos = fileInfos;
    }

    public Map<String, Object> getFileinfosMap() {
        return fileinfosMap;
    }

    public void setFileinfosMap(Map<String, Object> fileinfosMap) {
        this.fileinfosMap = fileinfosMap;
    }

}
