package com.saloon.saloon_mobile_api.service;


import com.saloon.saloon_mobile_api.dto.CreateEmployeeRequest;
import com.saloon.saloon_mobile_api.dto.EmployeeResponse;
import com.saloon.saloon_mobile_api.dto.UpdateEmployeeRequest;
import com.saloon.saloon_mobile_api.entity.Employee;
import com.saloon.saloon_mobile_api.entity.User;
import com.saloon.saloon_mobile_api.mapper.EmployeeMapper;
import com.saloon.saloon_mobile_api.repository.EmployeeRepository;
import com.saloon.saloon_mobile_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final UserRepository userRepo;

    private final EmployeeMapper employeeMapper;



    @Override
    public EmployeeResponse create(CreateEmployeeRequest request) {

        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (employeeRepo.existsByUser(user)) {
            throw new RuntimeException("Employee already exists for this user.");
        }

        Employee employee = new Employee();

        employee.setUser(user);
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());
        employee.setAddress(request.getAddress());

        Employee saved = employeeRepo.save(employee);

        return employeeMapper.toResponse(saved);
    }

    @Override
    public List<EmployeeResponse> getAll() {

        return employeeRepo.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    public EmployeeResponse get(Long id) {

        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        return employeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse update(
            Long id,
            UpdateEmployeeRequest request) {

        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

//        employee.setUser(user);
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());
        employee.setAddress(request.getAddress());

        Employee updated = employeeRepo.save(employee);

        return employeeMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {

        Employee existing = employeeRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        employeeRepo.delete(existing);
    }

}
