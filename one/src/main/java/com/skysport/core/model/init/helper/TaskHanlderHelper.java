package com.skysport.core.model.init.helper;

import com.skysport.core.cache.TaskHanlderCachedMap;
import com.skysport.inerfaces.bean.task.TaskHanlderVo;
import org.apache.commons.collections.map.HashedMap;
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
                vo.setTaskDefineKey(child.attributeValue("taskDefineKey").replaceAll("\\s", ""));
                vo.setName(child.attributeValue("name").replaceAll("\\s", ""));
                vo.setBusinessController(child.elementText("businessController").replaceAll("\\s", ""));
                vo.setBusinessService(child.elementText("businessService").replaceAll("\\s", ""));
                vo.setUrlInfo(child.elementText("urlInfo").replaceAll("\\s", ""));
                vo.setUrlPass(child.elementText("urlPass").replaceAll("\\s", ""));
                vo.setUrlReject(child.elementText("urlReject").replaceAll("\\s", ""));
                vo.setUrlSumit(child.elementText("urlSumit").replaceAll("\\s", ""));
                vos.add(vo);
            } catch (Exception e) {
                logger.error("catch one exception when parsing task-handle.xml ", e);
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
