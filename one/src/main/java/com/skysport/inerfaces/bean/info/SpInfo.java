package com.skysport.inerfaces.bean.info;

import com.skysport.core.bean.page.PageInfo;

/**
 * 供应商信息
 */
public class SpInfo extends PageInfo {

	private int id;
	/**
	 * 供应商编号
	 */
	private String spId;
	/**
	 * 供应商名称
	 */
	private String name;
	/**
	 * 供应商类型
	 */
	private String type;
	/**
	 * 联系人
	 */
	private String contact;
	/**
	 * 联系电话
	 */
	private String tel;
	/**
	 * 邮件地址
	 */
	private String email;

	/**
	 * 合作时间
	 */
	private String cooperationTime;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 备注
	 */
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCooperationTime() {
		return cooperationTime;
	}

	public void setCooperationTime(String cooperationTime) {
		this.cooperationTime = cooperationTime;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
