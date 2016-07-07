package com.skysport.interfaces.engine.listener.devp;

import com.skysport.core.bean.SpringContextHolder;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.model.permission.userinfo.service.IStaffService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * 说明:
 * Created by zhangjh on 2016-05-16.
 */
@Component
@Transactional
public class BomInfoStartListenr implements ExecutionListener {
    protected transient Log log = LogFactory.getLog(getClass());

    public int nextInt() {
        Random random = new Random();
        return random.nextInt(899999) + 100000;

    }

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        RuntimeService runtimeService = SpringContextHolder.getBean("runtimeService");
        IStaffService developStaffImpl = SpringContextHolder.getBean("developStaffImpl");


        String groupIdDevManager = developStaffImpl.getManagerStaffGroupId();
        String groupIdDev = developStaffImpl.getStaffGroupId();
        String bomId = (String) execution.getVariable("bomId");

        execution.setVariable(WebConstants.DEVLOP_STAFF_GROUP, groupIdDev);
        execution.setVariable(WebConstants.DEVLOP_MANAGER, groupIdDevManager);

        runtimeService.updateBusinessKey(execution.getProcessInstanceId(), bomId);

    }
}
