package com.telecom.hz.sample.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telecom.hz.sample.domain.Project;
import com.telecom.hz.sample.exception.NamingConflictException;
import com.telecom.hz.sample.exception.NotExistException;
import com.telecom.hz.sample.exception.OperationDenyException;
import com.telecom.hz.sample.mapper.ProjectMapper;
import com.telecom.hz.sample.mapper.VirtualMachineMapper;

/**
 * the implementation of project service.
 * 
 * @author 853976819@qq.com
 * @version v1.0
 * @date 2017年8月4日 下午5:15:05
 */
@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private VirtualMachineMapper vmMapper;

	@Transactional
	@Override
	public Project newOne(long userId, Project proj) throws SQLException, NamingConflictException {
		// TODO Auto-generated method stub
		Project _proj = projectMapper.getByUserIdAndProjectName(userId, proj.getName());
		if (_proj != null) {
			throw new NamingConflictException(String.format(
					"the new project's name '%s' conflict with the exist one '%s'"
					, proj.getName(), _proj.getName()));
		}
		proj.setUserId(userId);
		projectMapper.createOne(proj);
		return projectMapper.getProjectAndMachinesByIdAndUserId(userId, proj.getId());
	}

	@Override
	public Project getOne(long userId, long pid) throws SQLException {
		return projectMapper.getProjectAndMachinesByIdAndUserId(userId, pid);
	}

	@Override
	public List<Project> listAll(long userId) throws SQLException {
		return projectMapper.findAllByUserId(userId);
	}

	@Transactional
	@Override
	public Project updateOne(long userId, Project newProj)
			throws SQLException, NamingConflictException, NotExistException, OperationDenyException{
		this.checkBeforeUpdate(userId, newProj);
//		this.update(userId, newProj);
		projectMapper.updateOne(newProj);
		return projectMapper.getProjectAndMachinesByIdAndUserId(userId, newProj.getId());
	}
	
	@Transactional
	@Override
	public Project updateOneOnConditions(long userId, Project newProj)
			throws SQLException, NamingConflictException, NotExistException, OperationDenyException {
		// TODO Auto-generated method stub
		this.checkBeforeUpdate(userId, newProj);
		projectMapper.updateOneOnConditions(newProj);
		return projectMapper.getProjectAndMachinesByIdAndUserId(userId, newProj.getId());
	}
	
	private void checkBeforeUpdate(long uid, Project newProj) throws SQLException, NotExistException, NamingConflictException, OperationDenyException {
		Project oldProj = projectMapper.getById(newProj.getId());
		if(oldProj == null) {
			throw new NotExistException("can't do update on a not exist project.");
		}
		if(oldProj.getUserId().longValue() != uid) {
			throw new OperationDenyException("u'r not allow to update the project.");
		}
		if(newProj.getName() != null && !newProj.getName().equals(oldProj.getName())) {
			Project sameNameProject = projectMapper.getByUserIdAndProjectName(uid, newProj.getName());
			if(sameNameProject != null) {
				throw new NamingConflictException(
						String.format("the new project's name '%s' conflict with the exist one '%s'."
								, newProj.getName(), oldProj.getName()));
			}
		}
	}

	@Transactional
	@Override
	public void removeOne(long userId, long pid) throws SQLException, NotExistException, OperationDenyException {
		Project proj = projectMapper.getById(pid);
		if(proj == null) {
			throw new NotExistException("cant do delete on a not exist project");
		}
		if(proj.getUserId().longValue() != userId) {
			throw new OperationDenyException("u'r not allow to delete the project.");
		}
		vmMapper.deleteAllByProjectId(pid);
		projectMapper.deleteOne(pid);
	}
}
















