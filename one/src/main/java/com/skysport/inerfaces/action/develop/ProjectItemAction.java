package com.skysport.inerfaces.action.develop;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.constant.DictionaryKeyConstant;
import com.skysport.core.model.seqno.service.IncrementNumber;
import com.skysport.core.utils.SeqCreateUtils;
import com.skysport.inerfaces.bean.develop.ProjectBomInfo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.form.develop.ProjectQueryForm;
import com.skysport.inerfaces.model.develop.project.helper.ProjectManageHelper;
import com.skysport.inerfaces.model.develop.project.service.IProjectItemManageService;
import com.skysport.inerfaces.model.develop.quoted.service.IQuotedService;
import com.skysport.inerfaces.utils.BuildSeqNoHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明:子项目
 * Created by zhangjh on 2015/8/26.
 */
@Scope("prototype")
@Controller
@RequestMapping("/development/project_item")
public class ProjectItemAction extends BaseAction<String, Object, ProjectBomInfo> {

    @Resource(name = "projectItemManageService")
    private IProjectItemManageService projectItemManageService;

    @Resource(name = "incrementNumber")
    private IncrementNumber incrementNumber;

    @Resource(name = "quotedService")
    private IQuotedService quotedService;

    /**
     * 此方法描述的是：展示list页面	 *
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @SystemControllerLog(description = "打开子项目列表页面")
    public ModelAndView search() {
        ModelAndView mav = new ModelAndView("/development/project/project-item-list");
        return mav;
    }

    /**
     * 此方法描述的是：展示add页面
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/add/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "新增子项目")
    public ModelAndView add(@PathVariable String natrualKey) {

        ModelAndView mav = new ModelAndView("/development/project/project-item-add");
        mav.addObject("natrualkey", natrualKey);
        return mav;
    }


    /**
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile
     *
     * @param fileLocation
     * @return
     */
    @RequestMapping(value = "/add/{natrualKey}", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "增加子项目")
    public Map<String, Object> add(@RequestParam("fileLocation") MultipartFile[] fileLocation, HttpServletRequest request) {


        for (MultipartFile file : fileLocation) {
            String filePath = "D:\\work\\project\\upload\\skysport\\project\\" + file.getOriginalFilename();
            // 判断文件是否为空
            try {
                // 文件保存路径
                // String uploadDir = request.getSession().getServletContext().getRealPath("/") + "upload/";
                File emptyFile = new File(filePath);
                if (!emptyFile.exists()) {
                    emptyFile.createNewFile();
                }
                // 转存文件
                file.transferTo(emptyFile);
            } catch (Exception e) {
                logger.error("保存上传文件异常", e);
            }


        }
        Map<String, Object> resultMap = new HashMap<String, Object>();

        // 重定向
        return resultMap;
    }


    /**
     * @return 导出报价表
     * @throws IOException
     */
    @RequestMapping("/download_offer/{natrualkeys}")
    @SystemControllerLog(description = "导出报价表")
    public ModelAndView downloadOffer(@PathVariable String natrualkeys, HttpServletRequest request, HttpServletResponse response) throws IOException {

        quotedService.download(request, response, natrualkeys);

        return null;
    }


    /**
     * @return 导出面辅料详细
     * @throws IOException
     */
    @RequestMapping("/export_materialdetail/{natrualkeys}")
    @SystemControllerLog(description = "导出面辅料详细")
    public ModelAndView exportMaterialDetail(@PathVariable String natrualkeys, HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, NoSuchMethodException, UnsupportedEncodingException, IllegalAccessException {

        projectItemManageService.exportMaterialDetail(request, response, natrualkeys);

        return null;
    }


    /**
     * 此方法描述的是：
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/search")
    @ResponseBody
    @SystemControllerLog(description = "查询子项目信息列表")
    public Map<String, Object> search(HttpServletRequest request) {

        //组件queryFory的参数
        ProjectQueryForm queryForm = new ProjectQueryForm();
        queryForm.setDataTablesInfo(convertToDataTableQrInfo(DictionaryKeyConstant.PROJECT_TABLE_COLULMN, request));
        ProjectBomInfo bomInfo = new ProjectBomInfo();
        bomInfo.setYearCode(request.getParameter("yearCode"));
        bomInfo.setCustomerId(request.getParameter("customerId"));
        bomInfo.setAreaId(request.getParameter("areaId"));
        bomInfo.setSeriesId(request.getParameter("seriesId"));
        queryForm.setProjectBomInfo(bomInfo);

        // 总记录数
        int recordsTotal = projectItemManageService.listInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(queryForm.getDataTablesInfo().getSearchValue())) {
            recordsFiltered = projectItemManageService.listFilteredInfosCounts(queryForm);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<ProjectBomInfo> infos = projectItemManageService.searchInfos(queryForm);

        ProjectManageHelper.turnIdToName(infos);
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
    @SystemControllerLog(description = "更细子项目")
    public Map<String, Object> edit(@RequestBody ProjectBomInfo info) {

        projectItemManageService.edit(info);

        return rtnSuccessResultMap("更新成功");
    }


    /**
     * 此方法描述的是：项目新增
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:35:09
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "增加子项目")
    public Map<String, Object> add(ProjectBomInfo info) {

//
        String kind_name = ProjectManageHelper.buildKindName(info);
        String seqNo = BuildSeqNoHelper.SINGLETONE.getFullSeqNo(kind_name, incrementNumber, WebConstants.PROJECT_SEQ_NO_LENGTH);
//        //年份+客户+地域+系列+NNN
//        String projectId = kind_name + seqNo;
        String projectId = SeqCreateUtils.newRrojectSeq(info.getSeriesId());
        //设置ID
        info.setNatrualkey(projectId);
        info.setSeqNo(seqNo);

        //保存项目信息
        projectItemManageService.add(info);


        return rtnSuccessResultMap("新增成功");

    }


    /**
     * @param natrualKey 主键id
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "查询子项目")
    public ProjectBomInfo info(@PathVariable String natrualKey) {


        ProjectBomInfo info = projectItemManageService.queryInfoByNatrualKey(natrualKey);
        return info;
    }

    /**
     * @param natrualKey
     * @return
     */
    @RequestMapping(value = "/del/{natrualKey}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "删除子项目")
    public Map<String, Object> del(@PathVariable String natrualKey) {
        projectItemManageService.del(natrualKey);
        return rtnSuccessResultMap("删除成功");
    }


    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> querySelectList(HttpServletRequest request) {
        String name = request.getParameter("name");
        List<SelectItem2> commonBeans = projectItemManageService.querySelectList(name);

        return rtSelectResultMap(commonBeans);
    }

    /**
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile
     *
     * @param files
     * @return
     */
    @RequestMapping("fileUpload")
    @SystemControllerLog(description = "上传子项目相关附件信息")
    public String fileUpload(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
        for (MultipartFile file : files) {
            // 判断文件是否为空
            if (!file.isEmpty()) {
                try {
                    // 文件保存路径
                    String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/"
                            + file.getOriginalFilename();
                    // 转存文件
                    file.transferTo(new File(filePath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 重定向
        return "redirect:/list.html";
    }


    /**
     * 读取上传文件中得所有文件并返回
     *
     * @return
     */
    @RequestMapping("file-list")
    @SystemControllerLog(description = "")
    public ModelAndView list(HttpServletRequest request) {
        String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
        ModelAndView mav = new ModelAndView("list");
        File uploadDest = new File(filePath);
        String[] fileNames = uploadDest.list();
        for (int i = 0; i < fileNames.length; i++) {
            //打印出文件名
            System.out.println(fileNames[i]);
        }
        return mav;
    }
}
