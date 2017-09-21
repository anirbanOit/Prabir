package com.example.crudexample.feature.employee.api.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.crudexample.exception.handler.AppExceptionHandler;
import com.example.crudexample.feature.employee.api.controller.EmployeeController;
import com.example.crudexample.feature.employee.api.message.response.AllUsersDetailResponse;
import com.example.crudexample.feature.employee.bean.EmployeeBean;
import com.example.crudexample.util.TestUtil;
import com.example.crudexample.utils.constant.AppConstant;

public class EmployeeControllerTest {
	private MockMvc mockMvc;
	private EmployeeController employeeController;

	@Mock
	private EmployeeBean employeeBean;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.employeeController = new EmployeeController(employeeBean);
		this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
				.setControllerAdvice(new AppExceptionHandler()).build();
	}

	@After
	public void tearDown() throws Exception {
		this.employeeBean = null;
		this.mockMvc = null;
		this.employeeController = null;
	}

	@Test
	public void test_getAllUser_Success() throws Exception {
		String accessorId = "1";
		String sessionToken = "test";
		when(employeeBean.processGetAllUserProfile(accessorId, sessionToken)).thenReturn(new AllUsersDetailResponse());
		mockMvc.perform(get("/employee/list?admin_id=1").header("session_token", "test")).andDo(print())
				.andExpect(status().is2xxSuccessful()).andExpect(new ResultMatcher() {
					@Override
					public void match(MvcResult result) throws Exception {
						String successStatusCode = TestUtil.parse((result.getResponse().getContentAsString()));
						System.out.println("Status Code: " + successStatusCode);
						assertEquals(AppConstant.GENERIC_SUCCESS_CODE, successStatusCode);
					}
				});
		verify(employeeBean).processGetAllUserProfile(accessorId, sessionToken);
	}

	@Test
	public void test_getAllUser_Failure_noHeader() throws Exception {
		String statusCode = "0000100101";
		mockMvc.perform(get("/employee/list?admin_id=1")).andDo(print()).andExpect(status().is4xxClientError())
				.andExpect(new ResultMatcher() {
					@Override
					public void match(MvcResult result) throws Exception {
						String errorStatusCode = TestUtil.parse((result.getResponse().getContentAsString()));
						System.out.println("Status Code: " + errorStatusCode);
						assertEquals(statusCode, errorStatusCode);
					}
				});
		verify(employeeBean, never()).processGetAllUserProfile(anyString(), anyString());
	}

	@Test
	public void test_getAllUser_Failure_noParam() throws Exception {
		String statusCode = "0000200122";
		mockMvc.perform(get("/employee/list").header("session_token", "test")).andDo(print())
				.andExpect(status().is4xxClientError()).andExpect(new ResultMatcher() {
					@Override
					public void match(MvcResult result) throws Exception {
						String errorStatusCode = TestUtil.parse((result.getResponse().getContentAsString()));
						System.out.println("Status Code: " + errorStatusCode);
						assertEquals(statusCode, errorStatusCode);
					}
				});
		verify(employeeBean, never()).processGetAllUserProfile(anyString(), anyString());
	}

	@Test
	public void test_getAllUser_Failure_IncorrectParam() throws Exception {
		String statusCode = "0000200122";
		mockMvc.perform(get("/employee/list?admin_i=1").header("session_token", "test")).andDo(print())
				.andExpect(status().is4xxClientError()).andExpect(new ResultMatcher() {
					@Override
					public void match(MvcResult result) throws Exception {
						String errorStatusCode = TestUtil.parse((result.getResponse().getContentAsString()));
						System.out.println("Status Code: " + errorStatusCode);
						assertEquals(statusCode, errorStatusCode);
					}
				});
		verify(employeeBean, never()).processGetAllUserProfile(anyString(), anyString());
	}
}
