package com.example.crudexample.handler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.crudexample.domain.EmployeeDomain;
import com.example.crudexample.dto.EmployeeDTO;
import com.example.crudexample.exception.NotFoundException;
import com.example.crudexample.handler.impl.EmployeeHandlerImpl;
import com.example.crudexample.mockdata.MockData;
import com.example.crudexample.utils.APIGroupCode;

public class EmployeeHandlerTest {
	private EmployeeHandlerImpl employeeHandlerImpl;
	
	@Mock
	private EmployeeDomain employeeDomain;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		employeeHandlerImpl = new EmployeeHandlerImpl();
		// Using reflection as autowired is not working in bean service
		ReflectionTestUtils.setField(employeeHandlerImpl, "employeeDomain", employeeDomain);
	}

	@After
	public void tearDown() throws Exception {
		employeeHandlerImpl = null;
		employeeDomain = null;
	}
	
	@Test
	public final void test_performGetUsersProfileDetail_Success() {
		final String errorCodePrefix = APIGroupCode.EMPLOYEE_GET_ALL_USERS.getGroupCode()
				+ APIGroupCode.EMPLOYEE_GET_ALL_USERS.getApiCode() + "000";

		when(employeeDomain.getAllUsersProfile()).thenReturn(MockData.getStubbedEmployeeList());

		List<EmployeeDTO> employeeDTO = employeeHandlerImpl.performGetUsersProfileDetail(errorCodePrefix);

		verify(employeeDomain).getAllUsersProfile();

		assertEquals(MockData.getStubbedEmployeeList().get(0).getAddress(), employeeDTO.get(0).getAddress());
	}
	
	@Test(expected = NotFoundException.class)
	public final void test_performGetUsersProfileDetail_Users_Null() {
		final String errorCodePrefix = APIGroupCode.EMPLOYEE_GET_ALL_USERS.getGroupCode()
				+ APIGroupCode.EMPLOYEE_GET_ALL_USERS.getApiCode() + "000";

		when(employeeDomain.getAllUsersProfile()).thenReturn(null);

		List<EmployeeDTO> employeeDTO = employeeHandlerImpl.performGetUsersProfileDetail(errorCodePrefix);

		verify(employeeDomain).getAllUsersProfile();
	}
	
	@Test(expected = NotFoundException.class)
	public final void test_performGetUsersProfileDetail_Users_Empty() {
		final String errorCodePrefix = APIGroupCode.EMPLOYEE_GET_ALL_USERS.getGroupCode()
				+ APIGroupCode.EMPLOYEE_GET_ALL_USERS.getApiCode() + "000";

		when(employeeDomain.getAllUsersProfile()).thenReturn(new ArrayList<>());

		List<EmployeeDTO> employeeDTO = employeeHandlerImpl.performGetUsersProfileDetail(errorCodePrefix);

		verify(employeeDomain).getAllUsersProfile();
	}
}
