package com.bankmanagement.app.service;

import com.bankmanagement.app.dto.CustomerDTO;
import com.bankmanagement.app.model.Customer;
import com.bankmanagement.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(String customerId) {
        return customerRepository.findById(customerId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public CustomerDTO getCustomerByIdCard(String idCard) {
        return customerRepository.findByIdCard(idCard)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    @Transactional
    public CustomerDTO updateCustomer(String customerId, CustomerDTO customerDTO) {
        Optional<Customer> existingCustomer = customerRepository.findById(customerId);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setName(customerDTO.getName());
            customer.setDob(customerDTO.getDob());
            customer.setAddress(customerDTO.getAddress());
            Customer updatedCustomer = customerRepository.save(customer);
            return convertToDTO(updatedCustomer);
        }
        return null;
    }

    @Transactional
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setIdCard(customer.getIdCard());
        dto.setName(customer.getName());
        dto.setDob(customer.getDob());
        dto.setAddress(customer.getAddress());
        return dto;
    }

    private Customer convertToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setCustomerId(dto.getCustomerId());
        customer.setIdCard(dto.getIdCard());
        customer.setName(dto.getName());
        customer.setDob(dto.getDob());
        customer.setAddress(dto.getAddress());
        return customer;
    }
} 