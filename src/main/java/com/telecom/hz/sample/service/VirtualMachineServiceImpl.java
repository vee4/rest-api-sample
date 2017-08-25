package com.telecom.hz.sample.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telecom.hz.sample.domain.Project;
import com.telecom.hz.sample.domain.User;
import com.telecom.hz.sample.domain.VirtualMachine;
import com.telecom.hz.sample.exception.NamingConflictException;
import com.telecom.hz.sample.exception.NotExistException;
import com.telecom.hz.sample.exception.OperationDenyException;
import com.telecom.hz.sample.mapper.ProjectMapper;
import com.telecom.hz.sample.mapper.UserMapper;
import com.telecom.hz.sample.mapper.VirtualMachineMapper;

@Service
public class VirtualMachineServiceImpl implements VirtualMachineService {

	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private VirtualMachineMapper vmMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Transactional
	@Override
	public VirtualMachine newOne(long uid, VirtualMachine vm) throws SQLException, NotExistException, OperationDenyException, NamingConflictException {
		Project project = projectMapper.getById(vm.getProjectId());
		if(project == null) {
			throw new NotExistException(
					String.format("the project '%l' associate with the vm does not exist."
							, vm.getProjectId().longValue()));
		}
		if(project.getUserId().longValue() != uid) {
			throw new OperationDenyException("u'r not allow to create this vm cause u'r not the owner of the project.");
		}
		VirtualMachine sameNameVM = vmMapper.getByProjectIdAndVMName(project.getId(), vm.getName());
		if(sameNameVM != null) {
			throw new NamingConflictException(
					String.format("the vm '%s' u create is conflict with the exist one."
							, vm.getName()));
		}
		vmMapper.createOne(vm);
		return vmMapper.getDetailsById(vm.getId());
	}
	
	@Override
	public VirtualMachine getOne(long uid, long vid) throws SQLException {
		// TODO Auto-generated method stub
		return vmMapper.getDetailsByIdAndUserId(uid, vid);
	}

	@Transactional
	@Override
	public VirtualMachine updateOne(long uid, VirtualMachine vm)
			throws SQLException, NotExistException, OperationDenyException, NamingConflictException {
		this.checkBeforeUpdate(uid, vm);
		vmMapper.updateOne(vm);
		return vmMapper.getDetailsById(vm.getId());
	}
	
	@Transactional
	@Override
	public VirtualMachine updateOneOnConditions(long uid, VirtualMachine vm)
			throws SQLException, NotExistException, OperationDenyException, NamingConflictException {
		this.checkBeforeUpdate(uid, vm);
		vmMapper.updateOnCondition(vm);
		return vmMapper.getDetailsById(vm.getId());
	}
	
	private void checkBeforeUpdate(long uid, VirtualMachine vm) throws NotExistException, SQLException, OperationDenyException, NamingConflictException {
		VirtualMachine oldVM = vmMapper.getDetailsById(vm.getId());
		if(oldVM == null) {
			throw new NotExistException("can't do update on a not exist virtual machine, check it plz.");
		}
		User user = userMapper.getUserByProjectId(oldVM.getProjectId());
		if(user.getId().longValue() != uid) {
			throw new OperationDenyException(
					String.format("u'r not allow to update the machine named '%s'."
							, oldVM.getName()));
		}
		if(vm.getName()!=null && !vm.getName().equals(oldVM.getName())) {
			VirtualMachine sameNameVM = vmMapper.getByProjectIdAndVMName(oldVM.getProjectId(), vm.getName());
			if(sameNameVM!=null) {
				throw new NamingConflictException(
						String.format("the name '%s' of the new virtual machine is conflict with the exist one."
								, vm.getName()));
			}
		}
	}

	@Override
	public void removeOne(long uid, long vid) throws SQLException, NotExistException, OperationDenyException {
		VirtualMachine oldVM = vmMapper.getDetailsById(vid);
		if(oldVM == null) {
			throw new NotExistException("can't do delete on a not exist virtual machine, check it plz.");
		}
		User user = userMapper.getUserByProjectId(oldVM.getProjectId());
		if(user.getId().longValue() != uid) {
			throw new OperationDenyException(
					String.format("u'r not allow to delete the machine named '%s'."
							, oldVM.getName()));
		}
		vmMapper.deleteOne(vid);
	}
}





















