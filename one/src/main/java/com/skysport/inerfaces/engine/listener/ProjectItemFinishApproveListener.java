package com.skysport.inerfaces.engine.listener;

import com.skysport.inerfaces.model.develop.bom.IBomService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 说明:项目最初审批
 * Created by zhangjh on 2016/4/13.
 */
@Component
public class ProjectItemFinishApproveListener implements JavaDelegate {

    @Resource(name = "bomManageService")
    private IBomService bomManageService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String projectId = execution.getProcessBusinessKey();
        List<String> allbomIds = bomManageService.queryAllBomIdsByProjectId(projectId);
        execution.setVariable("allbomIds", allbomIds);//所有的bomid
//        RuntimeService runtimeService = execution.getEngineServices().getRuntimeService();

    }
}
