package com.telecom.hz.sample.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * 项目实体
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年8月4日  下午3:12:02
 */
public class Project {
	
	private Long id;
	
	@NotNull(message="the value of 'userId' can't be null")
	@Min(value=1, message="the value of 'userId' must be an active number")
	private Long userId;
	 
	@NotNull(message="the value 'name' can't be null.")
	@Size(max=32, min=1, message="the size of 'name' must in range 1 to 32.")
	@Pattern(regexp = "[0-9a-zA-Z\u4E00-\u9FA5 -<>]{1,32}"
	, message="the value of 'name' must in character set {number(0-9), letter('a-zA-Z'), whitespace(' '), transverse line('-')}")
	private String name;
	
	@Size(max=32, min=0, message="the length of 'tag' cant greater than 32.")
	@Pattern(regexp = "[0-9a-zA-Z\u4E00-\u9FA5 -<>]{1,32}"
	, message="the value of 'tag' must in character set {number(0-9), letter('a-zA-Z'), whitespace(' '), transverse line('-')}")
	private String tag;
	
	@Size(max=255, min=0, message="the length of 'description' can't greater than 255.")
	@Pattern(regexp = "[0-9a-zA-Z\u4E00-\u9FA5 -<>]{1,32}"
	, message="the value of 'description' must in character set {number(0-9), letter('a-zA-Z'), whitespace(' '), transverse line('-')}")
	private String description;
	
	private Timestamp createTime;
	
	List<VirtualMachine> machines;

	public Project() {
	}

	public Project(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	public List<VirtualMachine> getMachines() {
		return machines;
	}

	public void setMachines(List<VirtualMachine> machines) {
		this.machines = machines;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", userId=" + userId + ", name=" + name + ", tag=" + tag + ", description="
				+ description + ", createTime=" + createTime + ", machines=" + machines + "]";
	}
}






