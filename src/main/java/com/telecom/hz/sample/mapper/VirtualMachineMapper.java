package com.telecom.hz.sample.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.telecom.hz.sample.domain.VirtualMachine;
import com.telecom.hz.sample.mapper.provider.VirtualMachineSQLProvider;

@Mapper
public interface VirtualMachineMapper {
	
	@Insert("INSERT INTO sm_virtual_machine(project_id, name, cpu_num, memory_size, ip_address) "
			+ "values(#{projectId}, #{name},#{cpuNum},#{memorySize},#{ipAddress})")
	int createOne(VirtualMachine vm) throws SQLException;
	
	@Select("SELECT * FROM sm_virtual_machine WHERE id=#{vid}")
	VirtualMachine getDetailsById(@Param("vid") long vid) throws SQLException;
	
	@Select("SELECT vm.* FROM sm_virtual_machine vm "
			+ "LEFT JOIN sm_project p ON p.id=vm.project_id "
			+ "LEFT JOIN sm_user u ON u.id=p.user_id "
			+ "WHERE vm.id=#{vid} AND u.id=#{uid}")
	VirtualMachine getDetailsByIdAndUserId(@Param("uid") long uid, @Param("vid") long vid) throws SQLException;
	
	@Select("SELECT vm.* FROM sm_virtual_machine vm "
			+ "WHERE vm.project_id=#{pid} AND vm.name=#{vName} LIMIT 1")
	VirtualMachine getByProjectIdAndVMName(@Param("pid") long pid, @Param("vName") String vName) throws SQLException;
	
	@Select("SELECT id, name FROM sm_virtual_machine WHERE project_id=#{pid}")
	List<VirtualMachine> findAllByProjectId(@Param("pid") long pid) throws SQLException;
	
	@Update("UPDATE sm_virtual_machine SET name=#{name}, cpu_num=#{cpuNum} "
			+ ", memory_size=#{memorySize}, ip_address=#{ipAddress} "
			+ "WHERE id=#{id}")
	int updateOne(VirtualMachine vm) throws SQLException;
	
	@UpdateProvider(method = "updateOne", type = VirtualMachineSQLProvider.class)
	int updateOnCondition(VirtualMachine vm);
	
	@Delete("DELETE FROM sm_virtual_machine WHERE id=#{vid}")
	int deleteOne(@Param("vid") long vid) throws SQLException;
	
	@Delete("DELETE FROM sm_virtual_machine WHERE project_id=#{pid}")
	int deleteAllByProjectId(@Param("pid") long pid) throws SQLException; 
}















