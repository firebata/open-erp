package com.skysport.inerfaces.model.info.main_color.helper;

import com.skysport.core.constant.CharConstant;
import com.skysport.inerfaces.bean.develop.ProjectBomInfo;
import com.skysport.inerfaces.bean.info.MainColor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/15.
 */
public enum MainColorHelper {
    SINGLETONE;


    /**
     * 将以逗号分隔的主颜色，转换成List
     *
     * @param info
     * @return
     */
    public List<MainColor> turnMainColorStrToList(ProjectBomInfo info) {
        String mainColors = info.getMainColorNames();
        List<MainColor> emptyList = new ArrayList<MainColor>();
        if (StringUtils.isNotBlank(mainColors)) {
            String[] mainColorArr = mainColors.split(CharConstant.COMMA);
            for (int index = 0; index < mainColorArr.length; index++) {
                MainColor mainColor = new MainColor();
                mainColor.setProjectId(info.getNatrualkey());
                mainColor.setProjectName(info.getName());
                mainColor.setMainColorName(mainColorArr[index]);
                emptyList.add(mainColor);
            }
        }
        return emptyList;
    }

    /**
     * 将项目主颜色集合转成成数组
     *
     * @param mainList
     * @return
     */
    public String[] turnListToArr(List<MainColor> mainList) {
        String[] emptyArr = new String[0];
        if (null != mainList && !mainList.isEmpty()) {
            emptyArr = new String[mainList.size()];
            for (int index = 0; index < mainList.size(); index++) {
                emptyArr[index] = mainList.get(index).getMainColorName();
            }
        }
        return emptyArr;
    }


}
