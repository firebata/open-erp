package com.skysport.inerfaces.model.develop.project.helper;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.exception.SkySportException;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.core.model.seqno.service.IncrementNumber;
import com.skysport.core.utils.SeqCreateUtils;
import com.skysport.inerfaces.bean.develop.ProjectBomInfo;
import com.skysport.inerfaces.bean.develop.ProjectCategoryInfo;
import com.skysport.inerfaces.bean.develop.ProjectInfo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.utils.BuildSeqNoHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public class ProjectManageHelper {

    private ProjectManageHelper() {

    }


    /**
     * 构建项目序列号对应的主键：年份+客户+地域+系列
     *
     * @param info
     * @return
     */
    public static String buildKindName(ProjectBomInfo info) {
        StringBuilder stringBuilder = new StringBuilder(16);
        stringBuilder.append(info.getYearCode());
        stringBuilder.append(info.getCustomerId());
        stringBuilder.append(info.getAreaId());
        stringBuilder.append(info.getSeriesId());
        return stringBuilder.toString();

    }

    /**
     * 组装项目名称：年份+客户+系列+序列号
     *
     * @param t ProjectBomInfo
     * @return 项目名称
     */
    public static String buildProjectName(ProjectBomInfo t) {
        StringBuilder stringBuilder = new StringBuilder();

        //年份
        String name = getYearName(t);
        stringBuilder.append(name);
        stringBuilder.append(CharConstant.BLANK);
//        //客户
//        name = getCustomerName(t);
//        stringBuilder.append(name);
//        stringBuilder.append(CharConstant.BLANK);
        //系列
        name = getSeriesName(t);
        stringBuilder.append(name);
//        stringBuilder.append(CharConstant.BLANK);
//        stringBuilder.append(t.getSeqNo());
        return stringBuilder.toString();
    }

    /**
     * 获取客户名称
     *
     * @param t
     * @return
     */
    private static String getCustomerName(ProjectBomInfo t) {
        List<SelectItem2> items;
        String id;
        String name;
        items = SystemBaseInfoCachedMap.SINGLETONE.popBom("customerItems");
        id = t.getCustomerId();
        name = SystemBaseInfoCachedMap.SINGLETONE.getName(items, id);
        return name;
    }

    /**
     * 获取系列名称
     *
     * @param t
     * @return
     */
    private static String getSeriesName(ProjectBomInfo t) {
        List<SelectItem2> items;
        String id;
        String name;
        items = SystemBaseInfoCachedMap.SINGLETONE.popBom("seriesItems");
        id = t.getSeriesId();
        name = SystemBaseInfoCachedMap.SINGLETONE.getName(items, id);
        return name;
    }

    /**
     * 获取二级品类
     *
     * @param categoryInfo
     * @return
     */
    private static String getCategoryName(ProjectCategoryInfo categoryInfo) {
        List<SelectItem2> items = SystemBaseInfoCachedMap.SINGLETONE.popProject("categoryBItems");
        String id = categoryInfo.getCategoryBid();
        return SystemBaseInfoCachedMap.SINGLETONE.getName(items, id);
    }

    /**
     * 获取年份名称
     *
     * @param t
     * @return
     */
    private static String getYearName(ProjectBomInfo t) {
        List<SelectItem2> items = SystemBaseInfoCachedMap.SINGLETONE.popBom("yearItems");
        String id = t.getYearCode();
        return SystemBaseInfoCachedMap.SINGLETONE.getName(items, id);
    }

    /**
     * 获取年份名称
     *
     * @param t ProjectBomInfo
     * @return
     */
    private static String getAreaName(ProjectBomInfo t) {
        List<SelectItem2> items = SystemBaseInfoCachedMap.SINGLETONE.popBom("areaItems");
        String id = t.getAreaId();
        return SystemBaseInfoCachedMap.SINGLETONE.getName(items, id);
    }


    /**
     * 将数据集合里面的id转换为Name(应该在ProjectBomInfo增加对应的name属性，但是这里采用重用id，不新增属性)
     *
     * @param infos List<ProjectBomInfo>
     */
    public static void turnIdToName(List<ProjectBomInfo> infos) {
        if (null != infos && !infos.isEmpty()) {
            for (ProjectBomInfo projectBomInfo : infos) {
                exchange(projectBomInfo);
            }
        }
    }

    /**
     * 转换项目中的id为name
     *
     * @param infos
     */
    public static void turnIdToNameInPorject(List<ProjectInfo> infos) {

        if (null != infos && !infos.isEmpty()) {
            for (ProjectInfo projectInfo : infos) {
                exchange(projectInfo);
            }
        }

    }


    private static void exchange(ProjectBomInfo projectBomInfo) {
        projectBomInfo.setAreaId(getAreaName(projectBomInfo));
        projectBomInfo.setSeriesId(getSeriesName(projectBomInfo));
        projectBomInfo.setCustomerId(getCustomerName(projectBomInfo));
        projectBomInfo.setYearCode(getYearName(projectBomInfo));
    }

    /**
     * 构建项目信息
     *
     * @param info
     * @return
     */
    public static ProjectInfo buildProjectInfo(IncrementNumber incrementNumber, ProjectInfo info) {

        if (StringUtils.isBlank(info.getNatrualkey()) || "null".equals(info.getNatrualkey())) {
            //构建项目id，名称等信息
            String projectId = SeqCreateUtils.newRrojectSeq(info.getSeriesId());
            //设置ID
            info.setNatrualkey(projectId);
            String kind_name = ProjectManageHelper.buildKindName(info);
            String seqNo = BuildSeqNoHelper.SINGLETONE.getFullSeqNo(kind_name, incrementNumber, WebConstants.PROJECT_SEQ_NO_LENGTH);
            info.setSeqNo(seqNo);
        }


        String name = ProjectManageHelper.buildProjectName(info);
        info.setName(name);
        info.setProjectName(name);

        return info;
    }

    /**
     * 构建项目品类信息
     *
     * @param info
     */
    public static ProjectInfo buildProjectCategoryInfo(ProjectInfo info) {
        List<ProjectCategoryInfo> categoryInfos = info.getCategoryInfos();
        for (ProjectCategoryInfo categoryInfo : categoryInfos) {
            categoryInfo.setProjectId(info.getNatrualkey());
            categoryInfo.setProjectName(info.getName());
        }
        info.setCategoryInfos(categoryInfos);
        return info;
    }


    /**
     * 组装子项目信息
     *
     * @param info     ProjectInfo
     * @param userInfo
     * @return
     */
    public static List<ProjectBomInfo> buildProjectBomInfosByProjectInfo(ProjectInfo info, UserInfo userInfo) {
        List<ProjectBomInfo> projectBomInfos = new ArrayList<>();
        List<ProjectCategoryInfo> projectCategoryInfos = info.getCategoryInfos();


        if (null != projectCategoryInfos && !projectCategoryInfos.isEmpty()) {
            int seq = 1;
            for (ProjectCategoryInfo categoryInfo : projectCategoryInfos) {
//
                info.setSeqNo(seq + "");
                ProjectBomInfo projectBomInfo;//直接将项目的大部分项目信息转存到子项目对象中
                try {
                    projectBomInfo = info.clone();
                } catch (CloneNotSupportedException e) {
                    throw new SkySportException("100003", "克隆对象失败");
                }

                String natrualkey = info.getNatrualkey() + seq;
                String name = buildProjectItemName(info, categoryInfo);
                projectBomInfo.setNatrualkey(natrualkey);
                projectBomInfo.setName(name);
                projectBomInfo.setProjectName(name);
                projectBomInfo.setCategoryAid(categoryInfo.getCategoryAid());
                projectBomInfo.setCategoryBid(categoryInfo.getCategoryBid());
                projectBomInfo.setParentProjectId(info.getNatrualkey());
                projectBomInfos.add(projectBomInfo);
                projectBomInfo.setCreater(userInfo.getAliases());
                seq++;
            }
        }
        return projectBomInfos;
    }


    private static String buildProjectItemName(ProjectInfo info, ProjectCategoryInfo categoryInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        //年份
        String name = getYearName(info);
        stringBuilder.append(name);
        stringBuilder.append(CharConstant.BLANK);
        //系列
        name = getSeriesName(info);
        stringBuilder.append(name);
        stringBuilder.append(CharConstant.BLANK);
        //二级分类
        name = getCategoryName(categoryInfo);
        stringBuilder.append(name);
//        stringBuilder.append(CharConstant.BLANK);

        return stringBuilder.toString();
    }


}
