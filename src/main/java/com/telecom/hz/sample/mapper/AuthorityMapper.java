package com.telecom.hz.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.telecom.hz.sample.domain.Authority;

/**
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年8月2日  下午12:45:57
 */
@Mapper
public interface AuthorityMapper {
	
	@Select("SELECT name, path FROM sm_authority")
	List<Authority> findAll();
}
