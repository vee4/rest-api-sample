package com.telecom.hz.sample.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.telecom.hz.sample.domain.Project;
import com.telecom.hz.sample.exception.NotExistException;
import com.telecom.hz.sample.exception.OperationDenyException;
import com.telecom.hz.sample.exception.NamingConflictException;

/**
 * the interface definition of the project.
 * 
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年8月4日 下午4:38:55
 */
@Service
public interface ProjectService {

	/**
	 * create a new project.
	 * @param userId id of the current user.
	 * @param proj the new project.
	 * @return the project created.
	 * @throws SQLException
	 * @throws NamingConflictException if the project name is conflict with the exist one.
	 */
	Project newOne(long userId, Project proj) throws SQLException, NamingConflictException;

	/**
	 * get a project details, including the virtual machines.
	 * @param userId the id of the user.
	 * @param pid the project id
	 * @return the project be found or null if not.
	 * @throws SQLException
	 */
	Project getOne(long userId, long pid) throws SQLException;

	/**
	 * find a list of project that the user owns.
	 * @param userId the id of the user.
	 * @return the list be found or null if not.
	 * @throws SQLException
	 */
	List<Project> listAll(long userId) throws SQLException;

	/**
	 * update a project by the new one.
	 * @param userId the id of the user.
	 * @param newProj the new one.
	 * @return the project after updated.
	 * @throws SQLException
	 * @throws NotExistException if the project being updated does not exist.
	 * @throws OperationDenyException the project does not belongs to the user.
	 * @throws NamingConflictException the name of the new project conflict with exist one.
	 */
	Project updateOne(long userId, Project newProj)
			throws SQLException, NamingConflictException, NotExistException, OperationDenyException;

	/**
	 * update a project accroding to the given new project.
	 * @param userId id of user.
	 * @param newProj the new project.
	 * @return
	 * @throws SQLException
	 * @throws NamingConflictException
	 * @throws NotExistException
	 * @throws OperationDenyException
	 */
	Project updateOneOnConditions(long userId, Project newProj)
			throws SQLException, NamingConflictException, NotExistException, OperationDenyException;
	
	/**
	 * remove a project, cascading remove the associating virtual machines also.
	 * @param userId the id of the user.
	 * @param pid the id of the project to remove.
	 * @throws SQLException
	 * @throws NotExistException the project being remove does not exist.
	 * @throws OperationDenyException the project does not belongs to the user.
	 */
	void removeOne(long userId, long pid) throws SQLException, NotExistException, OperationDenyException;
}





