package com.telecom.hz.sample.service;

import java.sql.SQLException;

import com.telecom.hz.sample.domain.VirtualMachine;
import com.telecom.hz.sample.exception.NamingConflictException;
import com.telecom.hz.sample.exception.NotExistException;
import com.telecom.hz.sample.exception.OperationDenyException;

/**
 * the interface definition of virtual machine.
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年8月9日  上午10:55:05
 */
public interface VirtualMachineService {
	
	
	/**
	 * create a new virtual machine, the vm is not in use by default which means 
	 * it does not belongs to any project.
	 * @param vm the virtual machine to be created.
	 * @return the one new created.
	 * @throws SQLException
	 * @throws NotExistException the associate project not exist.
	 * @throws OperationDenyException the current user not the project owner.
	 * @throws NamingConflictException the new vm's name is conflict with the exist one.
	 */
	VirtualMachine newOne(long uid, VirtualMachine vm) throws SQLException, NotExistException,OperationDenyException, NamingConflictException;
	
	/**
	 * get a vm details.
	 * @param uid user id
	 * @param vid virtual machine id
	 * @return the vm that matches the select condition, or null if not exist
	 * or the current user has no permission to look over it.
	 * @throws SQLException
	 */
	VirtualMachine getOne(long uid, long vid) throws SQLException;
	
	/**
	 * entirely update a vm by the new given project.
	 * @param uid user id
	 * @param vm the new vm
	 * @return the vm after update.
	 * @throws SQLException
	 * @throws NotExistException 
	 * @throws NamingConflictException if the name of the new vm is 
	 * equals to the exist one, then the exception will be throw.
	 * @throws OperationDenyException the user does not have the permission to do such update
	 */
	VirtualMachine updateOne(long uid, VirtualMachine vm) throws SQLException, NotExistException, OperationDenyException, NamingConflictException;

	/**
	 * partial update the virtual machine according to the new given project.
	 * @param uid id of the user.
	 * @param vm the new virtual machine.
	 * @return the vm after update.
	 * @throws SQLException
	 * @throws NotExistException 
	 * @throws OperationDenyException the user does not have the permission to do such update
	 * @throws NamingConflictException if the name of the new vm is 
	 * equals to the exist one, then the exception will be throw.
	 */
	VirtualMachine updateOneOnConditions(long uid, VirtualMachine vm) throws SQLException, NotExistException, OperationDenyException, NamingConflictException;

	/**
	 * remove the virtual machine.
	 * @param uid the id of the user.
	 * @param vid the id of the virtual machine.
	 * @throws SQLException
	 * @throws NotExistException
	 * @throws OperationDenyException
	 */
	void removeOne(long uid, long vid) throws SQLException, NotExistException, OperationDenyException;
















}
