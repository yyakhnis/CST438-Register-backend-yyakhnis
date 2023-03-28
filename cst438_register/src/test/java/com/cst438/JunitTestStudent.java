package com.cst438;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.cst438.controller.StudentController;
import com.cst438.domain.Student;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.StudentRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.context.ContextConfiguration;

/* 
 * Example of using Junit with Mockito for mock objects
 *  the database repositories are mocked with test data.
 *  
 * Mockmvc is used to test a simulated REST call to the RestController
 * 
 * the http response and repository is verified.
 * 
 *   Note: This tests uses Junit 5.
 *  ContextConfiguration identifies the controller class to be tested
 *  addFilters=false turns off security.  (I could not get security to work in test environment.)
 *  WebMvcTest is needed for test environment to create Repository classes.
 */
@ContextConfiguration(classes = { StudentController.class })
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
public class JunitTestStudent {

	static final String URL = "http://localhost:8080";
	public static final String TEST_STUDENT_EMAIL = "junit@csumb.edu";
	public static final String TEST_STUDENT_NAME = "junit";
	public static final int TEST_STUDENT_ID = 1;

	public static final String TEST_STUDENT_EMAIL_1 = "test@csumb.edu";
	public static final String TEST_STUDENT_NAME_1 = "test";

	@MockBean
	StudentRepository studentRepository;

	@Autowired
	private MockMvc mvc;

	@Test
	public void addStudent()  throws Exception {
		
		MockHttpServletResponse response;
		
		Student student = new Student();
		student.setEmail(TEST_STUDENT_EMAIL);
		student.setName(TEST_STUDENT_NAME);
		student.setStatusCode(0);
		student.setStudent_id(1);
		
		// given  -- stubs for database repositories that return test data
	    given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(null);
	    given(studentRepository.save(any(Student.class))).willReturn(student);
	  
	    // create the DTO (data transfer object) for the student to add.  primary key student_id is 1.
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.name = TEST_STUDENT_NAME;
		studentDTO.email = TEST_STUDENT_EMAIL;
		
		// then do an http post request with body of studentDTO as JSON
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student")
			      .content(asJsonString(studentDTO))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		// verify that returned data has the expected value for "name"
		StudentDTO result = fromJsonString(response.getContentAsString(), StudentDTO.class);
		assertEquals(TEST_STUDENT_NAME  , result.name);
		
		// verify that returned data has the expected value for "email"
		assertEquals(TEST_STUDENT_EMAIL  , result.email);
		
		// verify that returned data has the expected value for "student_id"
		assertNotEquals(0  , result.student_id);
		
		// verify that repository save method was called.
		verify(studentRepository).save(any(Student.class));
		
	}
	
	/*@Test
	public void addStudent()  throws Exception {
		
		MockHttpServletResponse response;
		
		Student student = new Student();
		student.setEmail(TEST_STUDENT_EMAIL);
		student.setName(TEST_STUDENT_NAME);
		student.setStatusCode(0);
		student.setStudent_id(1);
		
		// given  -- stubs for database repositories that return test data
	    given(studentRepository.findByEmail(TEST_STUDENT_EMAIL)).willReturn(null);
	    given(studentRepository.save(any(Student.class))).willReturn(student);
		
		// then do an http post request with body of studentDTO as JSON
		response = mvc.perform(
				MockMvcRequestBuilders
			      .post("/student")
			      .content(asJsonString(student))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		// verify that returned data has the expected value for "name"
		Student result = fromJsonString(response.getContentAsString(), Student.class);
		assertEquals(TEST_STUDENT_NAME  , result.getName());
		
		// verify that returned data has the expected value for "email"
		assertEquals(TEST_STUDENT_EMAIL  , result.getEmail());
		
		// verify that returned data has the expected value for "student_id"
		assertEquals(1  , result.getStudent_id());
		
		// verify that repository save method was called.
		verify(studentRepository).save(any(Student.class));
		
	}*/
	
	@Test
	public void placeHold()  throws Exception {
		
		
		MockHttpServletResponse response;
		
		Student student = new Student();
		student.setEmail(TEST_STUDENT_EMAIL);
		student.setName(TEST_STUDENT_NAME);
		student.setStatusCode(0);
		student.setStudent_id(1);
		
		// given  -- stubs for database repositories that return test data
	    given(studentRepository.findById(TEST_STUDENT_ID)).willReturn(Optional.of(student));
	    given(studentRepository.save(any(Student.class))).willReturn(student);
	  

		// then do an http post request with body of courseDTO as JSON
		response = mvc.perform(
				MockMvcRequestBuilders
			      .put("/student/placeHold/1")
			      .content(asJsonString(student))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		// verify that returned data has non zero primary key
		Student result = fromJsonString(response.getContentAsString(), Student.class);
		assertEquals(TEST_STUDENT_NAME  , result.getName());
				
		assertEquals(TEST_STUDENT_EMAIL  , result.getEmail());
		
		assertEquals(1, result.getStudent_id());
		
		assertEquals("Holds", result.getStatus());
		
		assertEquals(1, result.getStatusCode());
				
		// verify that repository save method was called.
		verify(studentRepository).save(any(Student.class));
		
	}
	
	@Test
	public void releaseHold()  throws Exception {
		
		
		MockHttpServletResponse response;
		
		Student student = new Student();
		student.setEmail(TEST_STUDENT_EMAIL);
		student.setName(TEST_STUDENT_NAME);
		student.setStatusCode(0);
		student.setStudent_id(1);
		
		// given  -- stubs for database repositories that return test data
	    given(studentRepository.findById(TEST_STUDENT_ID)).willReturn(Optional.of(student));
	    given(studentRepository.save(any(Student.class))).willReturn(student);
	  

		// then do an http post request with body of courseDTO as JSON
		response = mvc.perform(
				MockMvcRequestBuilders
			      .put("/student/releaseHold/1")
			      .content(asJsonString(student))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// verify that return status = OK (value 200) 
		assertEquals(200, response.getStatus());
		
		// verify that returned data has non zero primary key
		Student result = fromJsonString(response.getContentAsString(), Student.class);
		assertEquals(TEST_STUDENT_NAME  , result.getName());
				
		assertEquals(TEST_STUDENT_EMAIL  , result.getEmail());
		
		assertEquals(1, result.getStudent_id());
		
		assertEquals("No Holds", result.getStatus());
		
		assertEquals(0, result.getStatusCode());
				
		// verify that repository save method was called.
		verify(studentRepository).save(any(Student.class));
		
	}
	
		
	private static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T  fromJsonString(String str, Class<T> valueType ) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}