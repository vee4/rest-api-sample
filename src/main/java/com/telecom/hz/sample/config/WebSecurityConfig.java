package com.telecom.hz.sample.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;

import com.alibaba.fastjson.JSON;
import com.telecom.hz.sample.domain.Authority;
import com.telecom.hz.sample.service.AuthorityService;
import com.telecom.hz.sample.service.CustomUserDetailsService;

/**
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年7月20日  上午9:32:19
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private AuthorityService authorityService;
	
	private static Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		CustomUserPasswordAuthenticationFilter authenticationFilter = new CustomUserPasswordAuthenticationFilter();
		authenticationFilter.setAuthenticationManager(providerManager());
		authenticationFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
		authenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
		authenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");
		//setup the authority
		List<Authority> authorities = authorityService.findAll();
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry configurer = http.authorizeRequests();
		for (Authority authority : authorities) {
			logger.warn(String.format("\n config authority '%s' of path '%s' \n", authority.getName(), authority.getPath()));
			configurer.antMatchers(authority.getPath()).hasAuthority(authority.getName());
		}
		//continue to config another configuration.
		configurer
			/*.accessDecisionManager(accessDecisionManager())*/.and()
			.csrf().ignoringAntMatchers("/api/v1/auth/login").disable()// csrf protection enable, enable by default
			.csrf().disable()
			.headers().referrerPolicy(ReferrerPolicy.SAME_ORIGIN)// same origin enable
				.and().and()
			.logout()
				.logoutUrl("/logout").permitAll()
				.invalidateHttpSession(true)
				.and()
			.addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	/**
	 * 认证管理
	 * @return
	 */
	@Bean("authenticationManager")
	public ProviderManager providerManager() {
		AuthenticationProvider[] providers = {daoAuthenticationProvider()};
		ProviderManager providerManager = new ProviderManager(Arrays.asList(providers));
		return providerManager;
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
		daoProvider.setUserDetailsService(userDetailsService);
		daoProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return daoProvider;
	}
	
	/**
	 * 密码加密方式
	 * @return 
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
//	
//	/**
//	 * 访问控制
//	 */
//	@Bean("accessDecisionManager")
//	public AccessDecisionManager accessDecisionManager() {
//		List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();
//		// 权限变更投票
//		decisionVoters.add(suspendVoter());
//		// 权限投票
//		decisionVoters.add(new RoleVoter());
//		// 认证投票
//		decisionVoters.add(new AuthenticatedVoter());
//		return new UnanimousBased(decisionVoters);
//	}
//	
//	@Bean
//	public MBeanExporter mBeanExporter() {
//		MBeanExporter mBeanExporter = new MBeanExporter();
//		Map<String, Object> beans = new HashMap<>();
//		beans.put("org.springframework.security:name=SuspendRealTimeVoter", suspendVoter());
//		mBeanExporter.setBeans(beans);
//		return mBeanExporter;
//	}
//	
//	@Bean("suspendVoter")
//	public SuspendRealTimeVoter suspendVoter() {
//		
//		return null;
//	}
}

class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
//		TODO: send the response text here while authenticate success
		User user = (User) authentication.getPrincipal();
		response.getWriter().write(JSON.toJSONString(user));
	}
}

class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
//		TODO: send the response text here while authenticate failure
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getWriter().write(JSON.toJSONString(exception.getMessage()));
	}
}












