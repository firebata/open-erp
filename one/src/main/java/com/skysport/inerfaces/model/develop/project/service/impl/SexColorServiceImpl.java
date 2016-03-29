package com.skysport.inerfaces.model.develop.project.service.impl;

import com.skysport.core.constant.CharConstant;
import com.skysport.inerfaces.bean.develop.SexColor;
import com.skysport.inerfaces.mapper.info.SexColorManageMapper;
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

    @Resource(name = "sexColorManageMapper")
    private SexColorManageMapper sexColorManageMapper;

    @Override
    public void afterPropertiesSet() {
        commonDao = sexColorManageMapper;
    }

    @Override
    public List<SexColor> searchInfos2(String natrualKey) {
        return sexColorManageMapper.searchInfos2(natrualKey);
    }

    @Override
    public void updateSexColorByProjectIdAndSexId(SexColor sexColor) {
        sexColorManageMapper.updateSexColorByProjectIdAndSexId(sexColor);
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
        List<SexColor> sexColors = searchInfos2(projectId);
        if (null != sexColors) {
            for (SexColor sexColor : sexColors) {
                String colorStr = sexColor.getMainColorNames();
                if (sexId.equals(sexColor.getSexIdChild())) {
                    SexColor sexColorNew = sexColor;
                    String[] colors = colorStr.split(CharConstant.COMMA);
                    List<String> newColors = new ArrayList<>();

                    for (String color : colors) {
                        if (color.trim().equals(mainColorOld)) {
                            newColors.add(mainColorNew);
                        } else {
                            newColors.add(color);
                        }
                    }
                    String colorStrNew = StringUtils.join(newColors, CharConstant.COMMA);
                    sexColorNew.setMainColorNames(colorStrNew);
                    updateSexColorByProjectIdAndSexId(sexColorNew);
                    return;
                }
            }
        }
    }

    @Override
    public void delByProjectId(String projectId) {
        sexColorManageMapper.delByProjectId(projectId);
    }
}
