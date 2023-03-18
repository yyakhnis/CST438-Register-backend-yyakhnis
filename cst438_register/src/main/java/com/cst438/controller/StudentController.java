package com.cst438.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@RestController
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	/*
	 * endpoint used by gradebook service to transfer final course grades
	 */
	@PostMapping("/student")
	@Transactional
	public StudentDTO newStudent( @RequestBody StudentDTO studentDTO) {
			
		Student student = new Student();
		student.setName(studentDTO.name);
		student.setEmail(studentDTO.email);
		
		Student existing_student = studentRepository.findByEmail(studentDTO.email);
		
		if (existing_student != null) {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student email already exists in the database.  "+studentDTO.email);
		} else {
			studentRepository.save(student);
			return studentDTO;
		}
	}
	
	
	@PutMapping("/student/placeHold/{student_id}")
	@Transactional
	public void placeHold(@PathVariable("student_id") int student_id) {
		
		Student student = studentRepository.findById(student_id).orElse(null);
		
		if (student != null) {
			int hold = 1;
			String hold_message = "Holds";
			student.setStatusCode(hold);
			student.setStatus(hold_message);
			studentRepository.save(student);
		} else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student_id invalid.  "+student_id);
		}
	}
	
	@PutMapping("/student/releaseHold/{student_id}")
	@Transactional
	public void releaseHold(@PathVariable("student_id") int student_id) {
		
		Student student = studentRepository.findById(student_id).orElse(null);
		
		if (student != null) {
			int no_hold = 0;
			String no_hold_message = "No Holds";
			student.setStatusCode(no_hold);
			student.setStatus(no_hold_message);
			studentRepository.save(student);
		} else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student_id invalid.  "+student_id);
		}
	}

}