package com.skysport.inerfaces.bean.task;

/**
 * 说明:任务任务和业务类的对应关系
 * Created by zhangjh on 2016/4/6.
 */
public class TaskHanlderVo {

    private String taskDefineKey;
    private String name;
    private String businessController;
    private String businessService;
    private String taskService;
    private String urlInfo;
    private String urlPass;
    private String urlReject;
    private String urlSumit;
    private String urlSave;

    public String getTaskService() {
        return taskService;
    }

    public void setTaskService(String taskService) {
        this.taskService = taskService;
    }

    public String getTaskDefineKey() {
        return taskDefineKey;
    }

    public void setTaskDefineKey(String taskDefineKey) {
        this.taskDefineKey = taskDefineKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessController() {
        return businessController;
    }

    public void setBusinessController(String businessController) {
        this.businessController = businessController;
    }

    public String getBusinessService() {
        return businessService;
    }

    public void setBusinessService(String businessService) {
        this.businessService = businessService;
    }

    public String getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(String urlInfo) {
        this.urlInfo = urlInfo;
    }

    public String getUrlPass() {
        return urlPass;
    }

    public void setUrlPass(String urlPass) {
        this.urlPass = urlPass;
    }

    public String getUrlReject() {
        return urlReject;
    }

    public void setUrlReject(String urlReject) {
        this.urlReject = urlReject;
    }

    public String getUrlSumit() {
        return urlSumit;
    }

    public void setUrlSumit(String urlSumit) {
        this.urlSumit = urlSumit;
    }

    public String getUrlSave() {
        return urlSave;
    }

    public void setUrlSave(String urlSave) {
        this.urlSave = urlSave;
    }
}
