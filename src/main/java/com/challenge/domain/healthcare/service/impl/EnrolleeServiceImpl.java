package com.challenge.domain.healthcare.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.challenge.domain.healthcare.constants.HealthCareApplicationConstants;
import com.challenge.domain.healthcare.exception.HealthServiceException;
import com.challenge.domain.healthcare.model.Dependent;
import com.challenge.domain.healthcare.model.Enrollee;
import com.challenge.domain.healthcare.repository.EnrolleeRepository;
import com.challenge.domain.healthcare.service.EnrolleeService;

@Service
public class EnrolleeServiceImpl implements EnrolleeService {

	@Autowired
	EnrolleeRepository enrolleeRepository;

	@Override
	public List<Enrollee> getAllEnrollees() {
		return (List<Enrollee>) enrolleeRepository.findAll();
	}

	@Override
	public Enrollee addEnrollee(Enrollee enrollee) throws HealthServiceException {
		try {
			return enrolleeRepository.save(enrollee);
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				throw new HealthServiceException(HealthCareApplicationConstants.ENROLLEE_MANDATORY_PROPERTIES_NOT_SET);
			}
		}
		return null;
	}

	@Override
	public Enrollee updateEnrollee(long enrolleeId, Enrollee e) throws HealthServiceException {
		Optional<Enrollee> enrollee = enrolleeRepository.findById(enrolleeId);
		if (enrollee.isPresent()) {
			enrollee.ifPresent(en -> {
				en.setName(e.getName());
				en.setBirthDate(e.getBirthDate());
				en.setPhoneNumber(e.getPhoneNumber());
				en.setDependents(e.getDependents());
				enrolleeRepository.save(en);
			});

			return e;

		} else {
			throw new HealthServiceException(
					HealthCareApplicationConstants.ENROLLEE_UPDATION_ERROR_MESSAGE + e.getId());
		}

	}

	@Override
	public String deleteAnEnrollee(long enrolleeId) throws HealthServiceException {
		Enrollee enrollee = enrolleeRepository.findById(enrolleeId).orElseThrow(
				() -> new HealthServiceException(HealthCareApplicationConstants.ENROLLEE_UPDATION_ERROR_MESSAGE));
		enrolleeRepository.delete(enrollee);
		return HealthCareApplicationConstants.ENROLLEE_DELETION_SUCCESS_MESSAGE;
	}

	@Override
	public Enrollee addDependentsToAnEnrollee(long enrolleeId, List<Dependent> dependents)
			throws HealthServiceException {
		try {
			Enrollee enrollee = enrolleeRepository.findById(enrolleeId).orElseThrow(
					() -> new HealthServiceException(HealthCareApplicationConstants.ENROLLEE_UPDATION_ERROR_MESSAGE));
			enrollee.getDependents().addAll(dependents);
			enrolleeRepository.save(enrollee);
			return enrollee;
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				throw new HealthServiceException(HealthCareApplicationConstants.ENROLLEE_MANDATORY_PROPERTIES_NOT_SET);
			}
		}

		return null;
	}

	@Override
	public Enrollee removeDependentsFromAnEnrollee(long enrolleeId, List<Dependent> dependents)
			throws HealthServiceException {
		Enrollee enrollee = enrolleeRepository.findById(enrolleeId).orElseThrow(
				() -> new HealthServiceException(HealthCareApplicationConstants.ENROLLEE_UPDATION_ERROR_MESSAGE));
		enrollee.getDependents().removeIf(item -> {
			if (dependents.stream().filter(d_given -> d_given.getId() == item.getId()).collect(Collectors.toList())
					.size() > 0)
				return true;
			return false;
		});
		enrolleeRepository.save(enrollee);
		return enrollee;
	}

	@Override
	public Enrollee modifyExistingDependents(long enrolleeId, List<Dependent> dependents)
			throws HealthServiceException {
		Enrollee enrollee = enrolleeRepository.findById(enrolleeId).orElseThrow(
				() -> new HealthServiceException(HealthCareApplicationConstants.ENROLLEE_UPDATION_ERROR_MESSAGE));
		enrollee.getDependents().stream().forEach(d -> {
			List<Dependent> deps = dependents.stream().filter(d_given -> d_given.getId() == d.getId())
					.collect(Collectors.toList());
			if (deps.size() > 0) {
				Dependent modifiedDependent = deps.get(0);
				d.setName(modifiedDependent.getName());
				d.setBirthDate(modifiedDependent.getBirthDate());
			}
		});
		enrolleeRepository.save(enrollee);

		return enrollee;
	}

}
