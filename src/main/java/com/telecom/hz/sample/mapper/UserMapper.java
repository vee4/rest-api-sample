package com.telecom.hz.sample.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.telecom.hz.sample.domain.User;

@Mapper()
public interface UserMapper {
	
	/**
	 * 根据用户名查找用户信息和权限信息。
	 * @param username
	 * @return
	 */
	@Select("SELECT u.*, a.name authority_name FROM sm_user u "
			+ "LEFT JOIN sm_user_role ur ON u.id=ur.user_id "
			+ "LEFT JOIN sm_role r ON r.id=ur.role_id "
			+ "LEFT JOIN sm_role_authority ra ON ra.role_id=r.id "
			+ "LEFT JOIN sm_authority a ON a.id=ra.authority_id "
			+ "WHERE username=#{username}")
	@ResultMap("userAuthoritiesMap")
	User findDetailsByUsername(@Param("username") String username) throws SQLException;
	
	@Select("SELECT u.* FROM sm_user u "
			+ "LEFT JOIN sm_project p ON p.user_id=u.id "
			+ "WHERE p.id=#{pid}")
	User getUserByProjectId(@Param("pid") long pid) throws SQLException;
	
	@Select("SELECT * FROM sm_user WHERE username=#{username}")
	User findByUsername(@Param("username") String username) throws SQLException;
}
