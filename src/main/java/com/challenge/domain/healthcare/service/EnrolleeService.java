package com.challenge.domain.healthcare.service;

import java.util.List;

import com.challenge.domain.healthcare.exception.HealthServiceException;
import com.challenge.domain.healthcare.model.Dependent;
import com.challenge.domain.healthcare.model.Enrollee;

public interface EnrolleeService {

	List<Enrollee> getAllEnrollees();

	Enrollee addEnrollee(Enrollee e) throws HealthServiceException;

	Enrollee updateEnrollee(long enrolleeId, Enrollee e) throws HealthServiceException;

	String deleteAnEnrollee(long enrolleeId) throws HealthServiceException;

	Enrollee addDependentsToAnEnrollee(long enrolleeId, List<Dependent> dependents) throws HealthServiceException;

	Enrollee removeDependentsFromAnEnrollee(long enrolleeId, List<Dependent> dependents) throws HealthServiceException;

	Enrollee modifyExistingDependents(long enrolleeId, List<Dependent> dependents) throws HealthServiceException;
}
