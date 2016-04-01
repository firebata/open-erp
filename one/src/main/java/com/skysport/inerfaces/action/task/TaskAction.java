package com.skysport.inerfaces.action.task;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.model.workflow.IWorkFlowService;
import com.skysport.core.utils.UserUtils;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.task.TaskInfo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.engine.workflow.helper.ProjectItemTaskHelper;
import com.skysport.inerfaces.form.task.TaskQueryForm;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class TaskAction extends BaseAction<TaskInfo> {
    @Autowired
    private IWorkFlowService projectItemTaskService;

    @RequestMapping(value = "/del/{natrualKey}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "删除主项目信息")
    public Map<String, Object> startWorkflow(@PathVariable String natrualKey) {
        ProcessInstance instance = projectItemTaskService.startProcessInstanceByKey(natrualKey);
        return rtnSuccessResultMap("启动成功");
    }


    /**
     * 此方法描述的是：显示待办任务页面
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/list/undo")
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
    @RequestMapping(value = "/list/done")
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
    @RequestMapping(value = "/list/todo")
    @ResponseBody
    @SystemControllerLog(description = "查询所有任务")
    public Map<String, Object> searchUndo(HttpServletRequest request) {
        // 总记录数
        int recordsTotal = 0;
        int recordsFiltered = 0;
        int draw = Integer.parseInt(request.getParameter("draw"));

        TaskQueryForm taskQueryForm = SpringContextHolder.getBean("taskQueryForm");
        TaskInfo taskInfo = ProjectItemTaskHelper.SINGLETONE.getTaskInfo(taskQueryForm, request);

        taskQueryForm.setDataTablesInfo(convertToDataTableQrInfo(WebConstants.PROJECT_TABLE_COLULMN, request));
        taskQueryForm.setTaskInfo(taskInfo);


        List<TaskInfo> infos = new ArrayList<>();


        Map<String, Object> resultMap = buildSearchJsonMap(infos, recordsTotal, recordsFiltered, draw);
        return resultMap;

    }

    /**
     * 此方法描述的是：查询已办任务信息
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/done/list")
    @ResponseBody
    @SystemControllerLog(description = "查询所有任务")
    public Map<String, Object> searchFinish(HttpSession session, HttpServletRequest request) {
        // 总记录数
        int recordsTotal = 0;
        int recordsFiltered = 0;
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<TaskInfo> infos = new ArrayList<>();
        UserInfo user = UserUtils.getUserFromSession(session);
        String userId = user.getNatrualkey();
//        List<Task> tasks = devlopmentTaskService.queryToDoTask(userId);

        Map<String, Object> resultMap = buildSearchJsonMap(infos, recordsTotal, recordsFiltered, draw);
        return resultMap;
    }

    /**
     * 此方法描述的是：
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:35:09
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "编辑项目信息")
    public Map<String, Object> edit(@RequestBody BomInfo info) {
        Map resultMap = rtnSuccessResultMap(MSG_UPDATE_SUCCESS);
        return resultMap;
    }

}
