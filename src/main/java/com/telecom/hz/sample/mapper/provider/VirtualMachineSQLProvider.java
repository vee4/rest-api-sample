package com.telecom.hz.sample.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

import com.telecom.hz.sample.domain.VirtualMachine;

public class VirtualMachineSQLProvider{
	
	public String updateOne(VirtualMachine vm) {
		return new SQL() {{
			UPDATE("sm_virtual_machine");
			if(vm.getProjectId() != null) {
				SET("project_id=#{projectId}");
			}
			if(vm.getName() != null) {
				SET("name=#{name}");
			}
			if(vm.getCpuNum() != null) {
				SET("cpu_num=#{cpuNum}");
			}
			if(vm.getMemorySize() != null) {
				SET("memory_size=#{memorySize}");
			}
			if(vm.getIpAddress() != null) {
				SET("ip_address=#{ipAddress}");
			}
			WHERE("id=#{id}");
		}}.toString();
	}
}
