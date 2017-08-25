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
import com.telecom.hz.sample.domain.Project;
import com.telecom.hz.sample.exception.IllegalParameterException;
import com.telecom.hz.sample.exception.NamingConflictException;
import com.telecom.hz.sample.exception.NotExistException;
import com.telecom.hz.sample.exception.OperationDenyException;
import com.telecom.hz.sample.service.ProjectService;
import com.telecom.hz.sample.utils.CommonUtils;

@RestController
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	private static Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@RequestMapping(value = "/api/v1/projects", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> newProject(@RequestBody() @Valid Project project, BindingResult result)
			throws SQLException, NamingConflictException, IllegalParameterException {
		// validate the input data.
		CommonUtils.handleValidateResult(result);
		// otherwise continue the service.
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Project newProj = projectService.newOne(userDetails.getUserId(), project);
		return ResponseEntity.status(HttpStatus.CREATED).body(newProj);
	}

	@RequestMapping(value = "/api/v1/projects", method = RequestMethod.GET)
	public ResponseEntity<Object> projects() throws SQLException {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return ResponseEntity.status(HttpStatus.OK).body(projectService.listAll(userDetails.getUserId()));
	}

	@RequestMapping(value = "/api/v1/projects/{pid}", method = RequestMethod.GET)
	public ResponseEntity<Project> project(@PathVariable("pid") long pid) throws SQLException, NotExistException {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return ResponseEntity.status(HttpStatus.OK).body(projectService.getOne(userDetails.getUserId(), pid));
	}

	@RequestMapping(value = "/api/v1/projects/{pid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Project> updateProjectEntirly(@PathVariable("pid") long pid,
			@RequestBody() @Valid Project proj, BindingResult result)
			throws SQLException, NamingConflictException, NotExistException, OperationDenyException, IllegalParameterException {
		CommonUtils.handleValidateResult(result);
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		proj.setId(pid);
		return ResponseEntity.status(HttpStatus.CREATED).body(projectService.updateOne(userDetails.getUserId(), proj));
	}

	@RequestMapping(value = "/api/v1/projects/{pid}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Project> updateProjectPartial(@PathVariable("pid") long pid, @RequestBody() Project proj)
			throws SQLException, NamingConflictException, NotExistException, OperationDenyException, IllegalParameterException {
		CommonUtils.validate(proj);
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		proj.setId(pid);
		return ResponseEntity.status(HttpStatus.CREATED).body(projectService.updateOneOnConditions(userDetails.getUserId(), proj));
	}

	@RequestMapping(value = "/api/v1/projects/{pid}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> removeProject(@PathVariable("pid") long pid)
			throws SQLException, NotExistException, OperationDenyException {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		projectService.removeOne(userDetails.getUserId(), pid);
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler(value = { SQLException.class, NotExistException.class, OperationDenyException.class,
			NamingConflictException.class, IllegalParameterException.class })
	@ResponseBody
	public ResponseEntity<String> exceptionHandler(Throwable ex) {
		logger.warn(Arrays.toString(ex.getStackTrace()));
		if (ex instanceof SQLException) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal server error occurs.");
		} else if (ex instanceof NotExistException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		} else if (ex instanceof OperationDenyException) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ex.getMessage());
		} else if (ex instanceof NamingConflictException) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		} else if (ex instanceof IllegalParameterException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unhandled exception.");
		}
	}
}
