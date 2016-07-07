package com.skysport.interfaces.engine.listener.devp;

import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.model.workflow.IWorkFlowService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 说明:项目最初审批
 * Created by zhangjh on 2016/4/13.
 */
@Component
@Transactional
public class ProjectItemPassListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String businessKey = execution.getProcessBusinessKey();//子项目id：启动子项目的时候，设置了业务主键
        IWorkFlowService projectItemTaskService = SpringContextHolder.getBean("projectItemTaskService");
        List<String> allbomIds = projectItemTaskService.invokePass(businessKey);
        execution.setVariable("allbomIds", allbomIds);//所有的bomids

    }
}
