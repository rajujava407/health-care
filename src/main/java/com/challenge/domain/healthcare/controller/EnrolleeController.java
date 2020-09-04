package com.challenge.domain.healthcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.domain.healthcare.model.Dependent;
import com.challenge.domain.healthcare.model.Enrollee;
import com.challenge.domain.healthcare.service.EnrolleeService;

import io.swagger.annotations.ApiOperation;

@RestController
public class EnrolleeController {

	@Autowired
	EnrolleeService enrolleeService;

	@GetMapping("/enrollees")
	@ApiOperation(
	        value = "Fetches the details of all the enrollees in the system")
	public List<Enrollee> getAllEnrollees() {
		return enrolleeService.getAllEnrollees();
	}

	@PostMapping("/addEnrollee")
	@ApiOperation(
	        value = "Creates an entry for the enrollee with all valid properties set")
	public ResponseEntity<Object> createEmployee(@RequestBody Enrollee enrollee) {
		ResponseEntity<Object> response;
		try {
			Enrollee e = enrolleeService.addEnrollee(enrollee);
			response = new ResponseEntity<>(e, HttpStatus.CREATED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;
	}

	@PutMapping("/updateEnrollee")
	@ApiOperation(
	        value = "Updates the details of an enrollee")
	public ResponseEntity<Object> updateEnrollee(@RequestParam(name = "id") long enrolleeId, @RequestBody Enrollee enrollee) {
		ResponseEntity<Object> response;
		try {
			Enrollee e = enrolleeService.updateEnrollee(enrolleeId, enrollee);
			response = new ResponseEntity<>(e, HttpStatus.CREATED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;
	}

	@DeleteMapping("/deleteEnrollee")
	@ApiOperation(
	        value = "Deletes an enrollee from the system")
	public ResponseEntity<Object> deleteEnrollee(@RequestParam(name = "id") long enrolleeId) {
		ResponseEntity<Object> response;
		try {
			String result = enrolleeService.deleteAnEnrollee(enrolleeId);
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;
	}

	@PutMapping("/addDependentsToAnEnrollee")
	@ApiOperation(
	        value = "Adds dependents to an enrollee in the system")
	public ResponseEntity<Object> addDependentsToAnEnrollee(@RequestParam(name = "id") long enrolleeId,
			@RequestBody List<Dependent> dependents) {
		ResponseEntity<Object> response;
		try {
			Enrollee result = enrolleeService.addDependentsToAnEnrollee(enrolleeId, dependents);
			response = new ResponseEntity<>(result, HttpStatus.CREATED);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;
	}

	@PutMapping("/removeDependentsFromAnEnrollee")
	@ApiOperation(
	        value = "Removes a dependent/(s) of the enrollee in the system")
	public ResponseEntity<Object> removeDependentsFromAnEnrollee(@RequestParam(name = "id") long enrolleeId,
			@RequestBody List<Dependent> dependents) {
		ResponseEntity<Object> response;
		try {
			Enrollee result = enrolleeService.removeDependentsFromAnEnrollee(enrolleeId, dependents);
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;
	}

	@PutMapping("/updateExistingDependents")
	@ApiOperation(
	        value = "Modify existing dependents of the enrollee in the system")
	public ResponseEntity<Object> updateExistingDependents(@RequestParam(name = "id") long enrolleeId,
			@RequestBody List<Dependent> dependents) {
		ResponseEntity<Object> response;
		try {
			Enrollee result = enrolleeService.modifyExistingDependents(enrolleeId, dependents);
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return response;
	}

}
