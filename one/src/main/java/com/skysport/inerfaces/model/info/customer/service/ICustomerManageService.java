package com.skysport.inerfaces.model.info.customer.service;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.inerfaces.bean.info.CustomerInfo;

import java.util.List;

/**
 * Created by zhangjh on 2015/6/3.
 */
public interface ICustomerManageService {

    /**
     * @return
     */
    public int listInfosCounts();

    /**
     * 过滤条件的记录数
     *
     * @param dataTablesInfo
     * @return 符合查询条件的pantone记录数
     */
    public int listFilteredCustomerInfosCounts(DataTablesInfo dataTablesInfo);

    /**
     * @param dataTablesInfo
     * @return
     */
    public List<CustomerInfo> searchCustomerInfos(DataTablesInfo dataTablesInfo);

    /**
     * @param customerInfo
     */
    public void edit(CustomerInfo customerInfo);

    /**
     * @param customerId
     * @return 根据pantoneid找出供应商信息
     */
    public CustomerInfo queryCustomerInfoByCustomerId(String customerId);

    public void add(CustomerInfo customerInfo);

    public void del(String customerId);
}
