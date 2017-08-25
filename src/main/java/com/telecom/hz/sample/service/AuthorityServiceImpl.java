package com.telecom.hz.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.hz.sample.domain.Authority;
import com.telecom.hz.sample.mapper.AuthorityMapper;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityMapper authorityMapper;
	
	@Override
	public List<Authority> findAll() {
		return authorityMapper.findAll();
	}
}
