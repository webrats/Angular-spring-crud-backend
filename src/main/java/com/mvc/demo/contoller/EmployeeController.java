package com.mvc.demo.contoller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.mvc.demo.exception.ResourceNotFound;
import com.mvc.demo.model.*;
import com.mvc.demo.repository.EmployeeRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200") 
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;
	
	
	
	@GetMapping("/employee")
	public List<Employee> getAllEmployee(){
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employee")
	public ResponseEntity<String> saveEmployee(@RequestBody Employee emp) {
		try {
			  employeeRepository.save(emp);
			return new  ResponseEntity<>("Employee saved ",HttpStatus.OK);
		} catch (Exception e) {
			return new  ResponseEntity<>("Employee not saved",HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
		Employee emp = employeeRepository.findById(id).orElseThrow(()->  new ResourceNotFound("Employee not exist by id "+id));
		return ResponseEntity.ok(emp);
	}
	
	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployeeById(@PathVariable long id, @RequestBody Employee employee){
		
		Employee emp= employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Employee not exist by id "+id));
	
	emp.setFirstName(employee.getFirstName());
	emp.setLastName(employee.getLastName());
	emp.setEmailId(employee.getEmailId());
	
	  Employee updatedEmployee =  employeeRepository.save(emp);
	  return ResponseEntity.ok(updatedEmployee);
	}
	
	@DeleteMapping("/employee/{id}")
	public  ResponseEntity<Map<String ,Boolean>> deleteEmployeeById(@PathVariable long id) {
		
		try {
			employeeRepository.deleteById(id);
			Map<String ,Boolean> response = new HashMap<>();
			response.put("delete", true);
			return  ResponseEntity.ok(response);
		}
		catch(Exception e) {
			throw new ResourceNotFound("Employee not exist by id "+id) ;
		}
		
	}
	
}
