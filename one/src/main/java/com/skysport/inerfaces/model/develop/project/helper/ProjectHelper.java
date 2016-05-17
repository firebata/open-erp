package com.skysport.inerfaces.model.develop.project.helper;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.exception.SkySportException;
import com.skysport.core.utils.UserUtils;
import com.skysport.inerfaces.utils.SeqCreateUtils;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.ProjectBomInfo;
import com.skysport.inerfaces.bean.develop.ProjectCategoryInfo;
import com.skysport.inerfaces.bean.develop.ProjectInfo;
import com.skysport.inerfaces.bean.relation.ProjectItemBomIdVo;
import com.skysport.inerfaces.bean.relation.ProjectItemProjectIdVo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.constant.develop.ReturnCodeConstant;
import com.skysport.inerfaces.utils.BuildSeqNoHelper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public enum ProjectHelper {
    SINGLETONE;

    public ProjectBomInfo getProjectBomInfo(HttpServletRequest request) {
        ProjectBomInfo bomInfo = new ProjectBomInfo();
        bomInfo.setYearCode(request.getParameter("yearCode"));
        bomInfo.setCustomerId(request.getParameter("customerId"));
        bomInfo.setAreaId(request.getParameter("areaId"));
        bomInfo.setSeriesId(request.getParameter("seriesId"));
        return bomInfo;
    }


    /**
     * 构建项目序列号对应的主键：年份+客户+地域+系列
     *
     * @param info
     * @return
     */
    public String buildKindName(ProjectBomInfo info) {
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
    public String buildProjectName(ProjectBomInfo t) {
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
    private String getCustomerName(ProjectBomInfo t) {
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
    private String getSeriesName(ProjectBomInfo t) {
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
    private String getCategoryName(ProjectCategoryInfo categoryInfo) {
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
    private String getYearName(ProjectBomInfo t) {
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
    private String getAreaName(ProjectBomInfo t) {
        List<SelectItem2> items = SystemBaseInfoCachedMap.SINGLETONE.popBom("areaItems");
        String id = t.getAreaId();
        return SystemBaseInfoCachedMap.SINGLETONE.getName(items, id);
    }


    /**
     * 将数据集合里面的id转换为Name(应该在ProjectBomInfo增加对应的name属性，但是这里采用重用id，不新增属性)
     *
     * @param infos List<ProjectBomInfo>
     */
    public void turnIdToName(List<ProjectBomInfo> infos) {
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
    public void turnIdToNameInPorject(List<ProjectInfo> infos) {
        if (null != infos && !infos.isEmpty()) {
            for (ProjectInfo projectInfo : infos) {
                exchange(projectInfo);
            }
        }

    }

    /**
     * @param projectBomInfo
     */
    private void exchange(ProjectBomInfo projectBomInfo) {
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
    public void buildProjectInfo(ProjectInfo info) {

        if (StringUtils.isBlank(info.getNatrualkey()) || "null".equals(info.getNatrualkey())) {
            //构建项目id，名称等信息
            String projectId = SeqCreateUtils.SINGLETONE.newRrojectSeq(info.getSeriesId());
            String kind_name = buildKindName(info);
            String seqNo = BuildSeqNoHelper.SINGLETONE.getFullSeqNo(kind_name, WebConstants.PROJECT_SEQ_NO_LENGTH);

            //设置ID
            info.setNatrualkey(projectId);
            info.setSeqNo(seqNo);
        }


        String name = buildProjectName(info);
        info.setName(name);
        info.setProjectName(name);
    }

    /**
     * 构建项目品类信息
     *
     * @param info
     */
    public ProjectInfo buildProjectCategoryInfo(ProjectInfo info) {
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
     * @param info ProjectInfo
     * @return
     */
    public List<ProjectBomInfo> buildProjectBomInfosByProjectInfo(ProjectInfo info, List<String> projectItemsDB) {
        String projectId = info.getNatrualkey();
        //读取session中的用户
        UserInfo userInfo = UserUtils.getUserFromSession();
        String aliases = userInfo.getAliases();
        List<ProjectBomInfo> projectBomInfos = new ArrayList<>();
        List<ProjectCategoryInfo> projectCategoryInfos = info.getCategoryInfos();
        if (null != projectCategoryInfos && !projectCategoryInfos.isEmpty()) {
            int seq = 1;
            for (ProjectCategoryInfo categoryInfo : projectCategoryInfos) {
                String projectItemId = SeqCreateUtils.SINGLETONE.newProjectItemSeq(categoryInfo);// info.getNatrualkey() + categoryInfo.getCategoryAid() + categoryInfo.getCategoryBid();
                String categoryAid = categoryInfo.getCategoryAid();
                String categoryBid = categoryInfo.getCategoryBid();
                String categoryAidBid = categoryAid + categoryBid;
                //子项目编号=大项目编号+一级品类+二级品类
                String name = buildProjectItemName(info, categoryInfo);
                for (String projectItemIdInDb : projectItemsDB) {
                    String timstamp = projectItemIdInDb.substring(0, 21);
                    String categoryAidBidInDB = projectItemIdInDb.substring(21);
                    if (categoryAidBid.equals(categoryAidBidInDB)) {
                        projectItemId = projectItemIdInDb;
                        break;
                    }

                }
                info.setSeqNo(String.valueOf(seq));
                ProjectBomInfo projectBomInfo;//直接将项目的大部分项目信息转存到子项目对象中
                try {
                    projectBomInfo = info.clone();
                } catch (CloneNotSupportedException e) {
                    throw new SkySportException(ReturnCodeConstant.SYS_EXP);
                }
                projectBomInfo.setNatrualkey(projectItemId);
                projectBomInfo.setName(name);
                projectBomInfo.setProjectName(name);
                projectBomInfo.setCategoryAid(categoryAid);
                projectBomInfo.setCategoryBid(categoryBid);
                projectBomInfo.setParentProjectId(projectId);
                projectBomInfo.setCreater(aliases);
                projectBomInfos.add(projectBomInfo);
                seq++;
            }
        }
        return projectBomInfos;
    }

    /**
     * @param info
     * @param categoryInfo
     * @return
     */
    private String buildProjectItemName(ProjectInfo info, ProjectCategoryInfo categoryInfo) {
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

    /**
     * @param projectBomInfos
     * @return
     */
    public List<String> buildBusinessKeys(List<ProjectBomInfo> projectBomInfos) {
        List<String> businessKeys = new ArrayList<>();
        for (ProjectBomInfo projectBomInfo : projectBomInfos) {
            businessKeys.add(projectBomInfo.getNatrualkey());
        }
        return businessKeys;
    }

    /**
     * @param projectBomInfos
     * @param projectId
     * @return
     */
    public List<ProjectItemProjectIdVo> getProjectItemProjectIdVo(List<ProjectBomInfo> projectBomInfos) {
        List<ProjectItemProjectIdVo> vos = new ArrayList<ProjectItemProjectIdVo>();
        for (ProjectBomInfo projectBomInfo : projectBomInfos) {
            String projectItemId = projectBomInfo.getNatrualkey();
            String projectPerentId = projectBomInfo.getParentProjectId();
            ProjectItemProjectIdVo vo = new ProjectItemProjectIdVo(projectItemId, projectPerentId);
            vos.add(vo);
        }

        return vos;
    }

//    /**
//     * @param projectBomInfos
//     * @param categoryInfosInDB
//     * @param natrualkey
//     * @return 交集
//     */
//    public List<ProjectBomInfo> getMatchList(List<ProjectBomInfo> projectBomInfos, List<ProjectCategoryInfo> categoryInfosInDB, String natrualkey) {
//        String projectId = natrualkey;
//        List<ProjectBomInfo> intersection = new ArrayList<>();
//        for (ProjectBomInfo projectBomInfo : projectBomInfos) {
//            String projectItemId = projectBomInfo.getNatrualkey();
//            for (ProjectCategoryInfo categoryInfo : categoryInfosInDB) {
//                //子项目编号=大项目编号+一级品类+二级品类
//                String projectItemIdDB = projectId + categoryInfo.getCategoryAid() + categoryInfo.getCategoryBid();
//                if (projectItemId.equals(projectItemIdDB)) {
//                    intersection.add(projectBomInfo);
//                    break;
//                }
//            }
//
//        }
//        return intersection;
//    }
//
//
//    public List<ProjectBomInfo> getNeedToDel(List<ProjectBomInfo> intersection, List<ProjectCategoryInfo> categoryInfosInDB, String natrualkey) {
//        List<ProjectBomInfo> needDelBomList = new ArrayList<>();
//        String projectId = natrualkey;
//
//        if (intersection.isEmpty()) {
//            for (ProjectCategoryInfo categoryInfo : categoryInfosInDB) {
//                String projectItemIdDB = projectId + categoryInfo.getCategoryAid() + categoryInfo.getCategoryBid();
//                ProjectBomInfo projectBomInfo = new ProjectBomInfo();
//                projectBomInfo.setNatrualkey(projectItemIdDB);
//                projectBomInfo.setProjectId(projectItemIdDB);
//                needDelBomList.add(projectBomInfo);
//            }
//        } else {
//            for (ProjectCategoryInfo categoryInfo : categoryInfosInDB) {
//                String projectItemIdDB = projectId + categoryInfo.getCategoryAid() + categoryInfo.getCategoryBid();
//
//
//            }
//        }
//        return needDelBomList;
//    }

    public List<String> buildProjectItemsId(List<ProjectBomInfo> projectBomInfos) {

        List<String> list = new ArrayList<>();
        for (ProjectBomInfo projectBomInfo : projectBomInfos) {
            String projectItemId = projectBomInfo.getNatrualkey();
            list.add(projectItemId);
        }
        return list;
    }

    public List<String> buildProjectItemsId(List<ProjectCategoryInfo> categoryInfosInDB, String projectId) {
        List<String> list = new ArrayList<>();
        for (ProjectCategoryInfo categoryInfo : categoryInfosInDB) {
            String projectItemIdDB = projectId + categoryInfo.getCategoryAid() + categoryInfo.getCategoryBid();
            list.add(projectItemIdDB);
        }
        return list;
    }

    /**
     * 获取匹配的
     *
     * @param intersection
     * @param projectBomInfos
     * @return
     */
    public List<ProjectBomInfo> getMatchProjectBomInfoList(List<String> intersection, List<ProjectBomInfo> projectBomInfos) {
        List<ProjectBomInfo> projectItems = new ArrayList<>();
        for (ProjectBomInfo projectBomInfo : projectBomInfos) {
            String projectItemId = projectBomInfo.getNatrualkey();
            for (String id : intersection) {
                if (projectItemId.equals(id)) {
                    projectItems.add(projectBomInfo);
                    break;
                }
            }
        }
        return projectItems;
    }

    /**
     * @param alls
     * @return
     */
    public List<ProjectItemBomIdVo> getProjectItemBomIdVo(List<BomInfo> alls) {
        List<ProjectItemBomIdVo> bomIdVos = new ArrayList<>();
        for (BomInfo bomInfo : alls) {
            String projectId = bomInfo.getProjectId();
            String bomId = StringUtils.isEmpty(bomInfo.getBomId()) ? bomInfo.getNatrualkey() : bomInfo.getBomId();
            ProjectItemBomIdVo vo = new ProjectItemBomIdVo();
            vo.setProjectId(projectId);
            vo.setBomId(bomId);
            bomIdVos.add(vo);
        }
        return bomIdVos;
    }
}
