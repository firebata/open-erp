package com.skysport.inerfaces.action.task;

import com.skysport.core.action.BaseAction;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.task.TaskInfo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
public class TaskAction extends BaseAction<String, Object, TaskInfo> {

    /**
     * 此方法描述的是：显示待办任务页面
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/list/undo")
    @ResponseBody
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
    public ModelAndView done() {
        ModelAndView mav = new ModelAndView("/task/list/done");
        return mav;
    }


    /**
     * 此方法描述的是：显示已办任务页面
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/list/all")
    @ResponseBody
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
    @RequestMapping(value = "/undo/list")
    @ResponseBody
    public Map<String, Object> searchUndo(HttpServletRequest request) {
        // 总记录数
        int recordsTotal = 0;
        int recordsFiltered = 0;
        int draw = Integer.parseInt(request.getParameter("draw"));
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
    public Map<String, Object> searchDown(HttpServletRequest request) {
        // 总记录数
        int recordsTotal = 0;
        int recordsFiltered = 0;
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<TaskInfo> infos = new ArrayList<>();
        Map<String, Object> resultMap = buildSearchJsonMap(infos, recordsTotal, recordsFiltered, draw);
        return resultMap;
    }


    /**
     * 此方法描述的是：查询所有任务
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/all/list")
    @ResponseBody
    public Map<String, Object> searchAll(HttpServletRequest request) {
        // 总记录数
        int recordsTotal = 0;
        int recordsFiltered = 0;
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<TaskInfo> infos = new ArrayList<>();
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
    public Map<String, Object> edit(@RequestBody BomInfo info) {
        Map resultMap = rtnSuccessResultMap(MSG_UPDATE_SUCCESS);
        return resultMap;
    }

}
