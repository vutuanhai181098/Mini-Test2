package com.example.minitest2.service;

import com.example.minitest2.entity.Employee;
import com.example.minitest2.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Page<Employee> getAllEmployee(Integer pageNumber, Integer pageSize, Sort sort){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return employeeRepository.findAll(pageable);
    }
}
