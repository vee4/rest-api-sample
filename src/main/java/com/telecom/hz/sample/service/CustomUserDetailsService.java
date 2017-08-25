package com.telecom.hz.sample.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.telecom.hz.sample.domain.Authority;
import com.telecom.hz.sample.domain.CustomUserDetails;
import com.telecom.hz.sample.domain.Role;
import com.telecom.hz.sample.mapper.UserMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.telecom.hz.sample.domain.User user = null;
		try {
			user = userMapper.findDetailsByUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(user == null) {
			throw new UsernameNotFoundException(String.format("username '%s' not found.", username));
		}
		// setup the authority.
		ArrayList<SimpleGrantedAuthority> authorityLst = new ArrayList<>();
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			Set<Authority> authorities = role.getAuthorities();
			for (Authority authority : authorities) {
				authorityLst.add(new SimpleGrantedAuthority(authority.getName()));
			}
		}
		return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword()
				, user.isEnabled(), true, true, true, authorityLst);
	}
	
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("12121122"));
	}
}
