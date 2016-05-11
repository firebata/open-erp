package com.skysport.inerfaces.engine.listener.devp;

import com.skysport.inerfaces.model.develop.bom.IBomService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * 说明:预报价后是否需要打色样的监听
 * Created by zhangjh on 2016/4/13.
 */
@Component("proQuoteFinishListener")
public class ProQuoteFinishListener implements JavaDelegate {

    @Resource(name = "bomManageService")
    private IBomService bomManageService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String projectId = execution.getProcessBusinessKey();
        List<String> allbomIds = bomManageService.queryAllBomIdsByProjectId(projectId);
        execution.setVariable("bomIdsNeedLapdip", allbomIds);//需要打色样的BOM
    }
}
