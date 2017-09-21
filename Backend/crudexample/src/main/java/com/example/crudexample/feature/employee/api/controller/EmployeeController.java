package com.example.crudexample.feature.employee.api.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudexample.api.message.response.AbstractResponse;
import com.example.crudexample.domain.repo.EmployeeRepo;
import com.example.crudexample.dto.EmployeeDTO;
import com.example.crudexample.feature.employee.bean.EmployeeBean;
import com.example.crudexample.utils.AppUtil;
import com.example.crudexample.utils.constant.AppConstant;
import com.example.crudexample.utils.log.AppLog;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	private final EmployeeBean employeeBean;
	private final AppLog appLog;
	EmployeeRepo rp;
	AbstractResponse getAllUserResponse;

	@Inject
	public EmployeeController(final EmployeeBean employeeBean) {
		this.appLog = AppLog.getInstance(EmployeeController.class);
		this.employeeBean = employeeBean;
	}

	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getUser() {
		return ResponseEntity.status(HttpStatus.OK).body("User Information");
	}

	@CrossOrigin
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AbstractResponse> getAllUser(@Valid @RequestParam("admin_id") final String accessorIdStr,
			@RequestHeader(AppConstant.REQUEST_HEADER_TOKEN) final String sessionToken) {
		String methodName = "getAllUser";

		appLog.printLog(Level.DEBUG, methodName, "Role: " + accessorIdStr);

		AbstractResponse getAllUserResponse = employeeBean.processGetAllUserProfile(accessorIdStr, sessionToken);

		appLog.printLog(Level.DEBUG, methodName, "Response: " + AppUtil.convertObjectToJson(getAllUserResponse));

		return ResponseEntity.status(HttpStatus.OK).body(getAllUserResponse);
	}
	
	
	
	@CrossOrigin
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<AbstractResponse> deleteUser(@PathVariable("id") final long id,
			@RequestHeader(AppConstant.REQUEST_HEADER_TOKEN) final String sessionToken) {
		String methodName = "deleteUser";

		AbstractResponse deleteUserResponse = employeeBean.processDelete(id);
		
		appLog.printLog(Level.DEBUG, methodName, "Response: " + AppUtil.convertObjectToJson(deleteUserResponse));
		
		return ResponseEntity.status(HttpStatus.OK).body(deleteUserResponse);
	}
	
	
	@CrossOrigin
	@RequestMapping(value="/addUser", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<AbstractResponse> addUser(
			@RequestHeader(AppConstant.REQUEST_HEADER_TOKEN) final String sessionToken,
			@Valid @RequestBody final EmployeeDTO employeeDTO){
		
		  String methodName = "addUsers";
		  appLog.printLog(Level.DEBUG, methodName, "token: " + sessionToken + " [] Request >> Body: " + AppUtil.convertObjectToJson(employeeDTO));
		  AbstractResponse abstractResponse = employeeBean.processAddUser(employeeDTO);
		  appLog.printLog(Level.DEBUG, methodName, "Response: " + AppUtil.convertObjectToJson(abstractResponse));
		  return ResponseEntity.status(HttpStatus.OK).body(abstractResponse);
	}
	
}
