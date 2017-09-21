package com.example.crudexample.feature.employee.bean;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.crudexample.feature.employee.api.message.response.AllUsersDetailResponse;
import com.example.crudexample.feature.employee.bean.impl.EmployeeBeanImpl;
import com.example.crudexample.handler.EmployeeHandler;
import com.example.crudexample.handler.RequestValidator;
import com.example.crudexample.mockdata.MockData;
import com.example.crudexample.utils.APIGroupCode;
import com.example.crudexample.utils.constant.AppConstant;

public class EmployeeBeanTest {
	private EmployeeBeanImpl employeeBeanImpl;

	@Mock
	private EmployeeHandler employeeHandler;

	@Mock
	private RequestValidator requestValidator;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		employeeBeanImpl = new EmployeeBeanImpl();
		// Using reflection as autowired is not working in bean service
		ReflectionTestUtils.setField(employeeBeanImpl, "employeeHandler", employeeHandler);
		ReflectionTestUtils.setField(employeeBeanImpl, "requestValidator", requestValidator);
	}

	@After
	public void tearDown() throws Exception {
		employeeBeanImpl = null;
		requestValidator = null;
		employeeHandler = null;
	}

	@Test
	public void test_processGetAllUserProfile_Success() {
		final String errorCodePrefix = APIGroupCode.EMPLOYEE_GET_ALL_USERS.getGroupCode()
				+ APIGroupCode.EMPLOYEE_GET_ALL_USERS.getApiCode() + "000";
		// Mock Data Create
		String accessorIdStr = "1";
		String sessionToken = AppConstant.TOKEN;
		when(employeeHandler.performGetUsersProfileDetail(errorCodePrefix))
				.thenReturn(MockData.getStubbedEmployeeDTOList());
		AllUsersDetailResponse allUsersDetailResponse = (AllUsersDetailResponse) employeeBeanImpl
				.processGetAllUserProfile(accessorIdStr, sessionToken);
		verify(employeeHandler).performGetUsersProfileDetail(errorCodePrefix);

		assertEquals(MockData.getStubbedEmployeeDTOList().get(0).getName(),
				allUsersDetailResponse.getEmployeeDTO().get(0).getName());
		assertEquals(AppConstant.API_STATUS_OK, allUsersDetailResponse.getStatusMessage());
	}
}
