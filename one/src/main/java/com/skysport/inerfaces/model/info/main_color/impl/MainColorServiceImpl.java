package com.skysport.inerfaces.model.info.main_color.impl;

import com.skysport.inerfaces.bean.info.MainColor;
import com.skysport.inerfaces.mapper.info.MainColorManageMapper;
import com.skysport.inerfaces.model.info.main_color.IMainColorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/15.
 */
@Service("mainColorService")
public class MainColorServiceImpl implements IMainColorService {

//    @Resource(name = "mainColorManageMapper")
//    private MainColorManageMapper mainColorManageMapper;
//
//    /**
//     * 根据项目id查询出所有主颜色
//     *
//     * @param projectId
//     * @return
//     */
//    @Override
//    public List<MainColor> queryMainColorList(String projectId) {
//        return mainColorManageMapper.queryMainColorList(projectId);
//    }
//
//    /**
//     * 保存项目中的主颜色信息
//     *
//     * @param mainColorList
//     */
//    @Override
//    public void add(List<MainColor> mainColorList) {
//        mainColorManageMapper.add(mainColorList);
//    }
//
//    /**
//     * @param natrualkey 项目id
//     */
//    @Override
//    public void delete(String natrualkey) {
//        mainColorManageMapper.delete(natrualkey);
//    }



}
