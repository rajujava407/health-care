package com.challenge.domain.healthcare.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.challenge.domain.healthcare.model.Enrollee;

@Repository
public interface EnrolleeRepository extends CrudRepository<Enrollee, Long> {

}
