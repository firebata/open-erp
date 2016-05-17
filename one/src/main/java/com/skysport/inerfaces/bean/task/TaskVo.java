package com.skysport.inerfaces.bean.task;

import com.skysport.inerfaces.bean.common.IdEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Map;

/**
 * 说明:任务Vo
 * Created by zhangjh on 2015/9/8.
 */
@Component
@Entity
public class TaskVo<T> extends IdEntity implements Serializable {

    private String name;
    private String createTime;
    private String taskDefinitionKey;
    private String assignee;
    //业务主键
    private String businessKey;
    //业务名称
    private String businessName;
    private boolean suspended;
    private int version;
    private String processInstanceId;
    private String processDefinitionId;

    public TaskVo() {
    }

//    // 运行中的流程实例
//    private ProcessInstance processInstance;
//
//    // 历史的流程实例
//    private HistoricProcessInstance historicProcessInstance;
//
//    // 流程定义
//    private ProcessDefinition processDefinition;

    private T t;
    private Map<String, Object> variables;


    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

}
