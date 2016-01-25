package com.skysport.core.model.idap.impl;
import com.skysport.core.mapper.IdapMapper;
import com.skysport.core.model.idap.IdapService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
/**
 * 说明:权限访问业务类
 * Created by zhangjh on 2015/12/25.
 */
@Service("idapService")
public class IdapServiceImpl implements IdapService {

    @Resource(name = "idapMapper")
    private IdapMapper idapMapper;

    /**
     * 查找所有开发人员
     *
     * @return 所有开发人员
     */
    @Override
    public List<String> findAllDeveloper() {
        return idapMapper.findAllDeveloper();
    }

    /**
     * 查找所有跟单组人员
     *
     * @return 所有跟单组人员（包括人员）
     */
    @Override
    public List<String> findAllOPer() {
        return idapMapper.findAllOPer();
    }

    /**
     * 查询开发部经理
     *
     * @return
     */
    @Override
    public List<String> findManagerOfDevDept() {
        return idapMapper.findManagerOfDevDept();
    }

    /**
     * 查询运营部经理
     *
     * @return
     */
    @Override
    public List<String> findManagerOfOP() {
        return idapMapper.findManagerOfOP();
    }









}
