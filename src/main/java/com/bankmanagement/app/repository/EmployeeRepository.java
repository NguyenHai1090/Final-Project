package com.bankmanagement.app.repository;

import com.bankmanagement.app.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByIdCard(String idCard);
    List<Employee> findByPosition(String position);
} 