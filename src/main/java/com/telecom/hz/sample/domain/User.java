package com.telecom.hz.sample.domain;

import java.sql.Timestamp;
import java.util.Set;

/**
 * 用户实体
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年8月2日  上午10:05:52
 */
public class User {
	
	private Long id;
	
	private String username;
	
	private String password;
	
	private boolean isEnabled;
	
	private Timestamp registerTime;
	
	private Set<Role> roles;
	
	public User() {
	}
	
	public User(Long id) {
		this.id = id.longValue();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User(String account, String password) {
		this.username = account;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Timestamp getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", isEnabled=" + isEnabled
				+ ", registerTime=" + registerTime + "]";
	}
}
