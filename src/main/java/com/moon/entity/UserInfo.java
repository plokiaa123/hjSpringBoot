package com.moon.entity;

import java.io.Serializable;

import javax.persistence.Entity;

public class UserInfo implements Serializable{
	
	private String dept;
	private String sex;
	private int pageNum;
	private int pageSize;
	public UserInfo(String dept, String sex, int pageNum, int pageSize) {
		super();
		this.dept = dept;
		this.sex = sex;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}
	public UserInfo() {
		super();
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "UserInfo [dept=" + dept + ", sex=" + sex + ", pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
	}
	
}
