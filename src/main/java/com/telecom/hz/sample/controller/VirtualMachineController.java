package com.telecom.hz.sample.controller;

import java.sql.SQLException;
import java.util.Arrays;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.hz.sample.domain.CustomUserDetails;
import com.telecom.hz.sample.domain.VirtualMachine;
import com.telecom.hz.sample.exception.IllegalParameterException;
import com.telecom.hz.sample.exception.NamingConflictException;
import com.telecom.hz.sample.exception.NotExistException;
import com.telecom.hz.sample.exception.OperationDenyException;
import com.telecom.hz.sample.service.VirtualMachineService;
import com.telecom.hz.sample.utils.CommonUtils;

@RestController
public class VirtualMachineController {
	
	private static Logger logger = LoggerFactory.getLogger(VirtualMachineController.class);
	
	@Autowired
	private VirtualMachineService vmService;

	@RequestMapping(value = "/api/v1/machines", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> newVM(@RequestBody @Valid VirtualMachine vm, BindingResult result) throws SQLException, NotExistException, OperationDenyException, NamingConflictException, IllegalParameterException {
		// validate the input data.
		CommonUtils.handleValidateResult(result);
		// otherwise continue the service.
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		VirtualMachine newVM = vmService.newOne(userDetails.getUserId(), vm);
		return ResponseEntity.status(HttpStatus.CREATED).body(newVM);
	}
	
	@RequestMapping(value = "/api/v1/machines/{vid}", method = RequestMethod.GET)
	public ResponseEntity<VirtualMachine> vm(@PathVariable("vid") long vid) throws SQLException {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return ResponseEntity.ok(vmService.getOne(userDetails.getUserId(), vid));
	}

	@RequestMapping(value = "/api/v1/machines/{vid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<VirtualMachine> update(@PathVariable("vid") long vid, @RequestBody @Valid VirtualMachine vm
			, BindingResult result) throws SQLException, NotExistException, OperationDenyException, NamingConflictException, IllegalParameterException {
		CommonUtils.handleValidateResult(result);
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		vm.setId(vid);
		return ResponseEntity.status(HttpStatus.CREATED).body(vmService.updateOne(userDetails.getUserId(), vm));
	}

	@RequestMapping(value = "/api/v1/machines/{vid}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<VirtualMachine> updateVM(@PathVariable("vid") long vid, @RequestBody VirtualMachine vm) throws SQLException, NotExistException, OperationDenyException, NamingConflictException, IllegalParameterException {
		CommonUtils.validate(vm);
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		vm.setId(vid);
		return ResponseEntity.status(HttpStatus.CREATED).body(vmService.updateOneOnConditions(userDetails.getUserId(), vm));
	}
	
	@RequestMapping(value = "/api/v1/machines/{vid}", method = RequestMethod.DELETE)
	public ResponseEntity<VirtualMachine> remove(@PathVariable("vid") long vid) throws SQLException, NotExistException, OperationDenyException, NamingConflictException {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		vmService.removeOne(userDetails.getUserId(), vid);
		return ResponseEntity.noContent().build();
	} 
	
	@ExceptionHandler(value = { SQLException.class, NotExistException.class, OperationDenyException.class, NamingConflictException.class, IllegalParameterException.class})
	@ResponseBody
	public ResponseEntity<String> exceptionHandler(Throwable ex) {
		logger.warn(Arrays.toString(ex.getStackTrace()));
		if(ex instanceof SQLException) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal server error occurs.");
		}else if(ex instanceof NotExistException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}else if(ex instanceof OperationDenyException) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
		}else if(ex instanceof NamingConflictException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}else if(ex instanceof IllegalParameterException){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unhandled exception.");
		}
	}
}










