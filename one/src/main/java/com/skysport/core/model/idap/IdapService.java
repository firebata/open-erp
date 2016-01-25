package com.skysport.core.model.idap;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/12/25.
 */
public interface IdapService {

    /**
     * 查找所有开发人员
     *
     * @return 所有开发人员
     */
    List<String> findAllDeveloper();

    /**
     * 查找所有跟单组人员
     *
     * @return 所有跟单组人员（包括人员）
     */
    List<String> findAllOPer();

    /**
     * 查询开发部经理
     *
     * @return
     */
    List<String> findManagerOfDevDept();

    /**
     * 查询运营部经理
     *
     * @return
     */
    List<String> findManagerOfOP();
    

}
