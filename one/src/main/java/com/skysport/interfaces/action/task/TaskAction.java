package com.skysport.interfaces.action.task;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.cache.TaskHanlderCachedMap;
import com.skysport.core.model.workflow.IApproveService;
import com.skysport.core.model.workflow.IWorkFlowService;
import com.skysport.core.utils.UserUtils;
import com.skysport.interfaces.bean.form.task.TaskQueryForm;
import com.skysport.interfaces.bean.task.TaskVo;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.engine.workflow.develop.helper.ProjectItemTaskHelper;
import com.skysport.interfaces.engine.workflow.helper.TaskServiceHelper;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明:工作流相关接口
 * Created by zhangjh on 2015/9/8.
 */
@Scope("prototype")
@Controller
@RequestMapping("/task")
public class TaskAction extends BaseAction<TaskVo> {
    @Autowired
    private IWorkFlowService taskServiceImpl;

    /**
     * 此方法描述的是：显示待办任务页面
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/todo/list")
    @ResponseBody
    @SystemControllerLog(description = "查询待办任务")
    public ModelAndView undo() {
        ModelAndView mav = new ModelAndView("/task/list/undo");
        return mav;
    }


    /**
     * 此方法描述的是：显示已办任务页面
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/done/list")
    @ResponseBody
    @SystemControllerLog(description = "查询已办任务")
    public ModelAndView done() {
        ModelAndView mav = new ModelAndView("/task/list/done");
        return mav;
    }


    /**
     * 此方法描述的是：显示所有任务
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/list/all")
    @ResponseBody
    @SystemControllerLog(description = "查询所有任务")
    public ModelAndView all() {
        ModelAndView mav = new ModelAndView("/task/list/all");
        return mav;
    }


    /**
     * 此方法描述的是：查询待办任务信息
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/todo/search")
    @ResponseBody
    @SystemControllerLog(description = "查询所有任务")
    public Map<String, Object> searchUndo(HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        // 总记录数
        int draw = Integer.parseInt(request.getParameter("draw"));
        TaskQueryForm taskQueryForm = SpringContextHolder.getBean("taskQueryForm");
        TaskVo taskInfo = ProjectItemTaskHelper.SINGLETONE.getTaskInfo(taskQueryForm, request);

        taskQueryForm.setDataTablesInfo(convertToDataTableQrInfo(WebConstants.PROJECT_TABLE_COLUMN, request));
        taskQueryForm.setTaskInfo(taskInfo);
        long recordsTotal = taskServiceImpl.queryTaskTotal(UserUtils.getUserFromSession().getNatrualkey());
        String userId = UserUtils.getUserFromSession().getNatrualkey();
        long recordsFiltered = taskServiceImpl.listFilteredPantoneInfosCounts(taskQueryForm, userId);
        List<TaskVo> infos = taskServiceImpl.queryToDoTaskFiltered(taskQueryForm, userId);
        Map<String, Object> resultMap = buildSearchJsonMap(infos, recordsTotal, recordsFiltered, draw);
        return resultMap;

    }

    /**
     * 此方法描述的是：查询已办任务信息
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/done/search")
    @ResponseBody
    @SystemControllerLog(description = "查询所有任务")
    public Map<String, Object> searchFinish(HttpSession session, HttpServletRequest request) {
        // 总记录数
        int recordsTotal = 0;
        int recordsFiltered = 0;
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<TaskVo> infos = new ArrayList<>();

        Map<String, Object> resultMap = buildSearchJsonMap(infos, recordsTotal, recordsFiltered, draw);
        return resultMap;
    }


    /**
     * 签收任务
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/claim/{taskId}/{businessKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "签收任务")
    public Map<String, Object> claim(@PathVariable String taskId, @PathVariable String businessKey) {
        taskServiceImpl.claim(taskId);
        return rtnSuccessResultMap("签收任务成功");
    }

    /**
     * 反签收任务
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/unclaim/{taskId}/{businessKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "反签收任务")
    public Map<String, Object> unclaim(@PathVariable String taskId, @PathVariable String businessKey) {
        taskServiceImpl.unclaim(taskId);
        return rtnSuccessResultMap("反签收任务成功");
    }


    /**
     * 此方法描述的是：处理任务：调转到指定的查询详情页面
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/handle/{taskId}/{businessKey}/{processInstanceId}")
    @ResponseBody
    @SystemControllerLog(description = "处理任务：调转到指定的查询详情页面")
    public ModelAndView handle(@PathVariable String taskId, @PathVariable String businessKey, @PathVariable String processInstanceId, HttpServletRequest request) {
        request.setAttribute("taskId", taskId);
        request.setAttribute("processInstanceId", processInstanceId);
        Task task = taskServiceImpl.createTaskQueryByTaskId(taskId);
        String taskDefinitionKey = task.getTaskDefinitionKey();
        String url = TaskHanlderCachedMap.SINGLETONE.queryValue(taskDefinitionKey).getUrlInfo();
        url = url + "/" + businessKey;
        ModelAndView mav = new ModelAndView("forward:" + url);
        return mav;
    }


    /**
     * 此方法描述的是：保存
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/save/{taskId}/{businessKey}")
    @ResponseBody
    @SystemControllerLog(description = "保存业务信息")
    public ModelAndView save(@PathVariable String taskId, @PathVariable String businessKey, HttpServletRequest request) {
        request.setAttribute("taskId", taskId);
        Task task = taskServiceImpl.createTaskQueryByTaskId(taskId);
        String taskDefinitionKey = task.getTaskDefinitionKey();
        String url = TaskHanlderCachedMap.SINGLETONE.queryValue(taskDefinitionKey).getUrlSave();
        url = url + taskId + "/" + businessKey;
        ModelAndView mav = new ModelAndView("forward:" + url);
        return mav;
    }


    /**
     * 此方法描述的是：表单提交
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/submit/{taskId}/{businessKey}")
    @ResponseBody
    @SystemControllerLog(description = "处理任务：调转到指定的查询详情页面")
    public ModelAndView submit(@PathVariable String taskId, @PathVariable String businessKey, HttpServletRequest request) {
        request.setAttribute("taskId", taskId);
        IApproveService approveService = TaskServiceHelper.getInstance().getIApproveService(taskId, taskServiceImpl);
        approveService.submit(taskId, businessKey);
        ModelAndView mav = new ModelAndView("forward:/task/todo/list");
        return mav;
    }


    /**
     * 此方法描述的是：审核通过
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "处理任务：调转到指定的查询详情页面")
    public ModelAndView pass(@RequestParam String businessKey, @RequestParam String taskId, @RequestParam String processInstanceId, @RequestParam String message, HttpServletRequest request) {
        Task task = taskServiceImpl.createTaskQueryByTaskId(taskId);
        IApproveService approveService = TaskServiceHelper.getInstance().getApproveService(task.getTaskDefinitionKey());
        Map<String, Object> variables = approveService.getVariableOfTaskNeeding(true, task);
//        taskServiceImpl.invokePass(businessKey,taskId,processInstanceId);
        taskServiceImpl.saveComment(taskId, processInstanceId, message);
        taskServiceImpl.complete(taskId, variables);
        approveService.updateApproveStatus(businessKey, WebConstants.APPROVE_STATUS_PASS);
        ModelAndView mav = new ModelAndView("forward:/task/todo/list");
        return mav;
    }


    /**
     * 此方法描述的是：驳回
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "处理任务：调转到指定的查询详情页面")
    public ModelAndView reject(@RequestParam String businessKey, @RequestParam String taskId, @RequestParam String processInstanceId, @RequestParam String message, HttpServletRequest request) {
//        String taskDefinitionKey = TaskServiceHelper.getInstance().getTaskDefinitionKey(taskId, taskServiceImpl);
        Task task = taskServiceImpl.createTaskQueryByTaskId(taskId);
        IApproveService approveService = TaskServiceHelper.getInstance().getApproveService(task.getTaskDefinitionKey());
        Map<String, Object> variables = approveService.getVariableOfTaskNeeding(false, task);
        taskServiceImpl.saveComment(taskId, processInstanceId, message);
        taskServiceImpl.complete(taskId, variables);
        approveService.updateApproveStatus(businessKey, WebConstants.APPROVE_STATUS_REJECT);
        ModelAndView mav = new ModelAndView("forward:/task/todo/list");
        return mav;
    }


    /**
     * 增加评论
     *
     * @param taskId
     * @param processInstanceId
     * @param message
     * @param session
     * @return
     */
    @RequestMapping(value = "/add_comment", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "增加审核评论")
    public ModelAndView saveComment(@RequestParam String taskId, @RequestParam String processInstanceId, @RequestParam String message, HttpSession session) {
        taskServiceImpl.saveComment(taskId, processInstanceId, message);
        ModelAndView mav = new ModelAndView("redirect:/task/todo/list");
        return mav;
    }

    /**
     * 查询所有评论
     *
     * @param processInstanceId
     * @return
     */
    @RequestMapping(value = "/list_comments/{processInstanceId}", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "增加审核评论")
    public Map<String, Object> listComments(@PathVariable String processInstanceId) {
        Map<String, Object> result = new HashedMap();
        List<Comment> taskComments = taskServiceImpl.getProcessInstanceComments(processInstanceId);
        List<HistoricTaskInstance> list = taskServiceImpl.createHistoricTaskInstanceQuery(processInstanceId);
        Map<String, String> taskNames = new HashMap<String, String>();
        if (null != list) {
            for (HistoricTaskInstance taskInstance : list) {
                taskNames.put(taskInstance.getId(), taskInstance.getName());
            }
        }
        result.put("comments", taskComments);
        result.put("taskNames", taskNames);
        return result;
    }


}
