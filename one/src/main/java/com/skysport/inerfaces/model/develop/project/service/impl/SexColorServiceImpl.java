package com.skysport.inerfaces.model.develop.project.service.impl;

import com.skysport.core.constant.CharConstant;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.SexColor;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.mapper.info.SexColorMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.model.develop.project.service.ISexColorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/11/11.
 */
@Service("sexColorService")
public class SexColorServiceImpl extends CommonServiceImpl<SexColor> implements ISexColorService, InitializingBean {

    @Resource(name = "sexColorMapper")
    private SexColorMapper sexColorMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = sexColorMapper;
    }

    @Override
    public List<SexColor> searchInfosByProjectId(String projectId) {
        return sexColorMapper.searchInfosByProjectId(projectId);
    }

    @Override
    public void updateSexColorByProjectIdAndSexId(SexColor sexColor) {
        sexColorMapper.updateSexColorByProjectIdAndSexId(sexColor);
    }

    /**
     * 只能改某个项目和某个性别的颜色信息
     *
     * @param sexId
     * @param mainColorNew
     * @param mainColorOld
     * @param projectId
     */
    @Override
    public void updateSexColorByProjectIdAndSexId(String sexId, String mainColorNew, String mainColorOld, String projectId) {
        int action = WebConstants.NEED_TO_UPDATE_PROJECT_SEX_COLOR;
        updateSexColor(sexId, mainColorOld, projectId, action, mainColorNew);
    }

    /**
     * 更新或者删除子项目某个性别对应的颜色
     *
     * @param sexId             性别属性
     * @param mainColorNeedDeal 需要处理的BOM主颜色（待更新的颜色，或者待删除的颜色）
     * @param projectId         项目id
     * @param action            行为
     * @param mainColorNew      主颜色信息
     */
    private void updateSexColor(String sexId, String mainColorNeedDeal, String projectId, int action, String mainColorNew) {

        SexColor sexColor = searchSexColorByProjectIdAndSexId(projectId, sexId);
        if (null != sexColor) {
            String colorStr = sexColor.getMainColorNames();
            if (sexId.equals(sexColor.getSexIdChild())) {
                SexColor sexColorNew = sexColor;
                String[] colors = colorStr.split(CharConstant.COMMA);
                List<String> newColors = new ArrayList<>();

                for (String color : colors) {
                    if (color.trim().equals(mainColorNeedDeal)) {
                        if (action == WebConstants.NEED_TO_UPDATE_PROJECT_SEX_COLOR) {
                            newColors.add(mainColorNew);
                        }
                    } else {
                        newColors.add(color);
                    }
                }
                String colorStrNew = StringUtils.join(newColors, CharConstant.COMMA);
                sexColorNew.setMainColorNames(colorStrNew);
                updateSexColorByProjectIdAndSexId(sexColorNew);
            }
        }
    }

    private SexColor searchSexColorByProjectIdAndSexId(String projectId, String sexId) {
        return sexColorMapper.searchSexColorByProjectIdAndSexId(projectId, sexId);
    }

    @Override
    public void delByProjectId(String projectId) {
        sexColorMapper.delByProjectId(projectId);
    }

    @Override
    public void delSexColorInfoByBomInfo(BomInfo info) {
        int action = WebConstants.NEED_TO_DEL_PROJECT_SEX_COLOR;
        String projectId = info.getProjectId();
        String sexId = info.getSexId();
        String mainColorNeedDel = info.getMainColor();
        updateSexColor(sexId, mainColorNeedDel, projectId, action, null);
    }
}
