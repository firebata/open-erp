package com.skysport.inerfaces.model.info.sp.service;

import com.skysport.core.bean.query.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.info.SpInfo;

import java.util.List;

/**
 * 
 * 此类描述的是：
 * 
 * @author: zhangjh
 * @version: 2015年4月29日 下午5:41:06
 */
public interface ISpManageService {


	
	/**
	 * @return
	 */
	int listSPInfosCounts();

	/**
	 * 过滤条件的记录数
	 * @param dataTablesInfo
	 * @return 符合查询条件的sp记录数
	 */
	int listFilteredSPInfosCounts(DataTablesInfo dataTablesInfo);

	/**
	 * 
	 * @param dataTablesInfo
	 * @return 
	 */
	List<SpInfo> searchSP(DataTablesInfo dataTablesInfo);
	
	/**
	 * 
	 * @param spInfo
	 */
	void edit(SpInfo spInfo);

	/**
	 *
	 * @param spId
	 * @return 根据spid找出供应商信息
	 */
	SpInfo querySpInfoBySpId(String spId);

	void add(SpInfo spInfo);

	void del(String spId);

	public List<SelectItem2> querySelectList(String name);
}
