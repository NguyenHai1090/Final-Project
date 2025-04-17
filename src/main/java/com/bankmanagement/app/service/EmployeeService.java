package com.bankmanagement.app.service;

import com.bankmanagement.app.dto.EmployeeDTO;
import com.bankmanagement.app.model.Employee;
import com.bankmanagement.app.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeById(String employeeId) {
        return employeeRepository.findById(employeeId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeByIdCard(String idCard) {
        return employeeRepository.findByIdCard(idCard)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesByPosition(String position) {
        return employeeRepository.findByPosition(position).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }

    @Transactional
    public EmployeeDTO updateEmployee(String employeeId, EmployeeDTO employeeDTO) {
        Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setName(employeeDTO.getName());
            employee.setDob(employeeDTO.getDob());
            employee.setAddress(employeeDTO.getAddress());
            employee.setSkillLevel(employeeDTO.getSkillLevel());
            employee.setSeniority(employeeDTO.getSeniority());
            employee.setPosition(employeeDTO.getPosition());
            Employee updatedEmployee = employeeRepository.save(employee);
            return convertToDTO(updatedEmployee);
        }
        return null;
    }

    @Transactional
    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setIdCard(employee.getIdCard());
        dto.setName(employee.getName());
        dto.setDob(employee.getDob());
        dto.setAddress(employee.getAddress());
        dto.setSkillLevel(employee.getSkillLevel());
        dto.setSeniority(employee.getSeniority());
        dto.setPosition(employee.getPosition());
        return dto;
    }

    private Employee convertToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setEmployeeId(dto.getEmployeeId());
        employee.setIdCard(dto.getIdCard());
        employee.setName(dto.getName());
        employee.setDob(dto.getDob());
        employee.setAddress(dto.getAddress());
        employee.setSkillLevel(dto.getSkillLevel());
        employee.setSeniority(dto.getSeniority());
        employee.setPosition(dto.getPosition());
        return employee;
    }
} 