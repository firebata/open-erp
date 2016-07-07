package com.skysport.core.model.init.helper;

import com.skysport.core.cache.TaskHanlderCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.interfaces.bean.task.TaskHanlderVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2016/4/6.
 */
public enum TaskHanlderHelper {

    SINGLETONE;
    Logger logger = LoggerFactory.getLogger(TaskHanlderHelper.class);

    public void init() throws IOException, DocumentException {
        TaskHanlderCachedMap.SINGLETONE.taskHanlderCachedMap = parseMap();
    }

    /**
     * 将XML解析成List
     *
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public List<TaskHanlderVo> parseList() throws IOException, DocumentException {
        SAXReader reader = new SAXReader();
        org.springframework.core.io.Resource fileRource = new ClassPathResource("conf/task-handle.xml");
        Document document = reader.read(fileRource.getFile());
        Element root = document.getRootElement();
        List<Element> childElements = root.elements();
        List<TaskHanlderVo> vos = new ArrayList<>();
        for (Element child : childElements) {

            TaskHanlderVo vo = new TaskHanlderVo();
            try {
                String taskDefineKey = child.attributeValue("taskDefineKey");
                taskDefineKey = StringUtils.isBlank(taskDefineKey) ? CharConstant.EMPTY : taskDefineKey.replaceAll("\\s", "");

                String name = child.attributeValue("name");
                name = StringUtils.isBlank(name) ? CharConstant.EMPTY : name.replaceAll("\\s", "");

                String businessController = child.elementText("businessController");
                businessController = StringUtils.isBlank(businessController) ? CharConstant.EMPTY : businessController.replaceAll("\\s", "");

                String businessService = child.elementText("businessService");
                businessService = StringUtils.isBlank(businessService) ? CharConstant.EMPTY : businessService.replaceAll("\\s", "");


                String taskService = child.elementText("taskService");
                taskService = StringUtils.isBlank(taskService) ? CharConstant.EMPTY : taskService.replaceAll("\\s", "");

                String urlInfo = child.elementText("urlInfo");
                urlInfo = StringUtils.isBlank(urlInfo) ? CharConstant.EMPTY : urlInfo.replaceAll("\\s", "");

                String urlPass = child.elementText("urlPass");
                urlPass = StringUtils.isBlank(urlPass) ? CharConstant.EMPTY : urlPass.replaceAll("\\s", "");

                String urlReject = child.elementText("urlReject");
                urlReject = StringUtils.isBlank(urlReject) ? CharConstant.EMPTY : urlReject.replaceAll("\\s", "");


                String urlSumit = child.elementText("urlSumit");
                urlSumit = StringUtils.isBlank(urlSumit) ? CharConstant.EMPTY : urlSumit.replaceAll("\\s", "");

                vo.setTaskDefineKey(taskDefineKey);
                vo.setName(name);
                vo.setBusinessController(businessController);
                vo.setBusinessService(businessService);
                vo.setTaskService(taskService);
                vo.setUrlInfo(urlInfo);
                vo.setUrlPass(urlPass);
                vo.setUrlReject(urlReject);
                vo.setUrlSumit(urlSumit);
                vos.add(vo);
            } catch (Exception e) {
                logger.error("catch one exception when parsing task-handle.xml ");
            }
        }
        return vos;
    }

    /**
     * 将XML解析成Map
     *
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public Map<String, TaskHanlderVo> parseMap() throws IOException, DocumentException {

        List<TaskHanlderVo> vos = parseList();
        Map<String, TaskHanlderVo> map = new HashedMap();
        for (TaskHanlderVo vo : vos) {
            String taskDefineKey = vo.getTaskDefineKey();
            map.put(taskDefineKey, vo);
        }
        return map;
    }


}
