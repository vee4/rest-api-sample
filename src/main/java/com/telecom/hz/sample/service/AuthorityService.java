package com.telecom.hz.sample.service;

import java.util.List;

import com.telecom.hz.sample.domain.Authority;

/**
 * 权限业务层接口定义
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年8月2日  下午12:45:06
 */
public interface AuthorityService {
	
	/**
	 * 获取权限列表
	 * @return
	 */
	List<Authority> findAll();
}
