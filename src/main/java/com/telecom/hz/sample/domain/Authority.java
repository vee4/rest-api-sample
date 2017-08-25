package com.telecom.hz.sample.domain;

import java.sql.Timestamp;

/**
 * 权限实体
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年8月2日  上午10:07:08
 */
public class Authority {
	
	private Long id;
	
	private String name;
	
	private String path;
	
	private String description;
	
	private Timestamp createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	@Override
	public String toString() {
		return "Authority [id=" + id + ", name=" + name + ", path=" + path + ", description=" + description
				+ ", createTime=" + createTime + "]";
	}
}
