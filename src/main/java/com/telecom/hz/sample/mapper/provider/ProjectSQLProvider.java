package com.telecom.hz.sample.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

import com.telecom.hz.sample.domain.Project;

public class ProjectSQLProvider{
	
	public String updateOne(Project newProject) {
		return new SQL() {{
			UPDATE("sm_project");
			if(newProject.getUserId() != null) {
				SET("user_id=#{userId}");
			}
			if(newProject.getName() != null) {
				SET("name=#{name}");
			}
			if(newProject.getTag() != null) {
				SET("tag=#{tag}");
			}
			if(newProject.getDescription() != null) {
				SET("description=#{description}");
			}
			WHERE("id=#{id}");
		}}.toString();
	}
}
