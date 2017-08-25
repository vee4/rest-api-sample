package com.telecom.hz.sample.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.telecom.hz.sample.domain.Project;
import com.telecom.hz.sample.mapper.provider.ProjectSQLProvider;

/**
 * interface and implementation of the project data access object.
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年8月4日  下午5:17:57
 */
@Mapper
public interface ProjectMapper {
	
	@Insert(value="INSERT INTO sm_project(user_id, name, tag, description) "
			+ "VALUES(#{userId}, #{name}, #{tag}, #{description})")
	void createOne(Project proj) throws SQLException;
	
	@Select("SELECT * FROM sm_project WHERE id=#{pid}")
	Project getById(@Param("pid") long pid) throws SQLException;
	
	@Select("SELECT p.*, vm.id vm_id, vm.name vm_name FROM sm_project p "
			+ "LEFT JOIN sm_virtual_machine vm ON vm.project_id=p.id "
			+ "WHERE p.id=#{projectId} AND p.user_id=#{uid} LIMIT 1")
	@ResultMap("projectDetailsMap")
	Project getProjectAndMachinesByIdAndUserId(@Param("uid") long uid, @Param("projectId") long pid) throws SQLException;
	
	@Select("SELECT id, name FROM sm_project WHERE user_id=#{uid}")
	List<Project> findAllByUserId(@Param("uid") long uid) throws SQLException;
	
	@Select("SELECT * FROM sm_project WHERE user_id=#{userId} AND name=#{projectName} LIMIT 1")
	Project getByUserIdAndProjectName(@Param("userId") long userId, @Param("projectName") String projectName) throws SQLException;
	
	@Update("UPDATE sm_project SET user_id=#{userId}, "
			+ "name=#{name}, tag=#{tag}, description=#{description} "
			+ "WHERE id=#{id}")
	int updateOne(Project newProject) throws SQLException;
	
	@UpdateProvider(method = "updateOne", type = ProjectSQLProvider.class)
	int updateOneOnConditions(Project newProject) throws SQLException;
	
	@Delete("DELETE FROM sm_project WHERE id=#{projectId}")
	int deleteOne(@Param("projectId") long projectId) throws SQLException;
}







