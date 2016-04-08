package com.skysport.inerfaces.action.task;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.cache.TaskHanlderCachedMap;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.model.workflow.IWorkFlowService;
import com.skysport.core.utils.UserUtils;
import com.skysport.inerfaces.bean.task.TaskVo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.engine.workflow.helper.ProjectItemTaskHelper;
import com.skysport.inerfaces.form.task.TaskQueryForm;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/9/8.
 */
@Scope("prototype")
@Controller
@RequestMapping("/task")
public class TaskAction extends BaseAction<TaskVo> {
    @Autowired
    private IWorkFlowService projectItemTaskService;

    @Autowired
    public TaskService taskService;


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
        int recordsTotal = 0;
        int recordsFiltered = 0;
        int draw = Integer.parseInt(request.getParameter("draw"));

        TaskQueryForm taskQueryForm = SpringContextHolder.getBean("taskQueryForm");
        TaskVo taskInfo = ProjectItemTaskHelper.SINGLETONE.getTaskInfo(taskQueryForm, request);

        taskQueryForm.setDataTablesInfo(convertToDataTableQrInfo(WebConstants.PROJECT_TABLE_COLUMN, request));
        taskQueryForm.setTaskInfo(taskInfo);


        List<TaskVo> infos = projectItemTaskService.queryToDoTask(UserUtils.getUserFromSession().getNatrualkey());

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
        UserInfo user = UserUtils.getUserFromSession(session);
        String userId = user.getNatrualkey();
//        List<Task> tasks = devlopmentTaskService.queryToDoTask(userId);

        Map<String, Object> resultMap = buildSearchJsonMap(infos, recordsTotal, recordsFiltered, draw);
        return resultMap;
    }


    /**
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/claim/{taskId}/{businessKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "签收任务")
    public Map<String, Object> claim(@PathVariable String taskId, @PathVariable String businessKey) {
        projectItemTaskService.claim(taskId);
        return rtnSuccessResultMap("签收任务成功");
    }


    /**
     * 此方法描述的是：处理任务：调转到指定的查询详情页面
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/handle/{taskId}/{businessKey}")
    @ResponseBody
    @SystemControllerLog(description = "处理任务：调转到指定的查询详情页面")
    public ModelAndView handle(@PathVariable String taskId, @PathVariable String businessKey, HttpServletRequest request) {
        request.setAttribute("taskId", taskId);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String taskDefinitionKey = task.getTaskDefinitionKey();
        String url = TaskHanlderCachedMap.SINGLETONE.queryValue(taskDefinitionKey).getUrlInfo();
        ModelAndView mav = new ModelAndView("forward:" + url + "/" + businessKey);
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
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String taskDefinitionKey = task.getTaskDefinitionKey();
        String url = TaskHanlderCachedMap.SINGLETONE.queryValue(taskDefinitionKey).getUrlSave();
        ModelAndView mav = new ModelAndView("forward:" + url + "/" + taskId + "/" + businessKey);
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
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String taskDefinitionKey = task.getTaskDefinitionKey();
        String url = TaskHanlderCachedMap.SINGLETONE.queryValue(taskDefinitionKey).getUrlSumit();
        ModelAndView mav = new ModelAndView("forward:" + url + "/" + taskId + "/" + businessKey);
        return mav;
    }


    /**
     * 此方法描述的是：审核通过
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/pass/{taskId}/{businessKey}")
    @ResponseBody
    @SystemControllerLog(description = "处理任务：调转到指定的查询详情页面")
    public ModelAndView pass(@PathVariable String taskId, @PathVariable String businessKey, HttpServletRequest request) {
        request.setAttribute("taskId", taskId);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String taskDefinitionKey = task.getTaskDefinitionKey();
        String url = TaskHanlderCachedMap.SINGLETONE.queryValue(taskDefinitionKey).getUrlPass();
        ModelAndView mav = new ModelAndView("forward:" + url + "/" + taskId + "/" + businessKey);
        return mav;
    }


    /**
     * 此方法描述的是：驳回
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/reject/{taskId}/{businessKey}")
    @ResponseBody
    @SystemControllerLog(description = "处理任务：调转到指定的查询详情页面")
    public ModelAndView reject(@PathVariable String taskId, @PathVariable String businessKey, HttpServletRequest request) {
        request.setAttribute("taskId", taskId);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String taskDefinitionKey = task.getTaskDefinitionKey();
        String url = TaskHanlderCachedMap.SINGLETONE.queryValue(taskDefinitionKey).getUrlReject();
        ModelAndView mav = new ModelAndView("forward:" + url + "/" + taskId + "/" + businessKey);
        return mav;
    }


}
