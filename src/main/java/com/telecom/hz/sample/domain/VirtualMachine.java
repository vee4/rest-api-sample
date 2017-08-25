package com.telecom.hz.sample.domain;

import java.sql.Timestamp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class VirtualMachine {
	
	private Long id;
	
	@NotNull(message="the project id that associate with the vm can't not be null")
	@Min(value = 1, message="the value of 'projectId' must be an active number")
	private Long projectId;
	
	@NotNull(message="the name of the virtual machine can not be null")
	@Size(min=1, max=32, message="the length of name must between 1 to 32")
	@Pattern(regexp = "[0-9a-zA-Z\u4E00-\u9FA5 -<>]{1,32}"
	, message="the value of 'name' must in character set {number(0-9), letter('a-zA-Z'), whitespace(' '), transverse line('-')}")
	private String name;
	
	@Min(value = 1, message="the value of 'cpuNum' must be an active number")
	private Integer cpuNum;
	
	@Min(value = 1, message="the value of 'memorySize' must be an active number")
	private Integer memorySize;
	
	@Pattern(regexp = "(((0?((0?[0-9])|([1-9][0-9])))|(1[0-9]{2})|(2(([0-4][0-9])|(5[0-5]))))\\.){3}((0?((0?[0-9])|([1-9][0-9])))|(1[0-9]{2})|(2(([0-4][0-9])|(5[0-5]))))"
			, message="invalid ip address")
	private String ipAddress;
	
//	public static void main(String[] args) {
//		String regex = "(((0?((0?[0-9])|([1-9][0-9])))|(1[0-9]{2})|(2(([0-4][0-9])|(5[0-5]))))\\.){3}((0?((0?[0-9])|([1-9][0-9])))|(1[0-9]{2})|(2(([0-4][0-9])|(5[0-5]))))";
//		System.out.println(java.util.regex.Pattern.matches(regex, "0&0.0.9"));
//	}
	
	private Timestamp createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCpuNum() {
		return cpuNum;
	}

	public void setCpuNum(Integer cpuNum) {
		this.cpuNum = cpuNum;
	}

	public Integer getMemorySize() {
		return memorySize;
	}

	public void setMemorySize(Integer memorySize) {
		this.memorySize = memorySize;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "VirtualMachine [id=" + id + ", projectId=" + projectId + ", name=" + name + ", cpuNum=" + cpuNum
				+ ", memorySize=" + memorySize + ", ipAddress=" + ipAddress + ", createTime=" + createTime + "]";
	}
}
