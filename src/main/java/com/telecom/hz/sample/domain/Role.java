package com.telecom.hz.sample.domain;

import java.sql.Timestamp;
import java.util.Set;

/**
 * 角色实体
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年8月2日  上午10:05:39
 */
public class Role {
	
	private Long id;
	
	private String name;
	
	private String description;
	
	private Timestamp createTime;
	
	private Set<Authority> authorities;

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
	
	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", description=" + description + ", createTime=" + createTime
				+ ", authorities=" + authorities + "]";
	}
}
