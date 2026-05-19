package com.example.employeeapi.service;

import com.example.employeeapi.dao.EmployeeDao;
import com.example.employeeapi.exception.EmployeeNotFoundException;
import com.example.employeeapi.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    // The service contains business flow between controller and DAO.
    private final EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public Employee getEmployeeById(Long id) {
        Employee employee = employeeDao.findById(id);

        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }

        return employee;
    }

    public Employee createEmployee(Employee employee) {
        Employee createdEmployee = employeeDao.addEmployee(employee);

        if (createdEmployee == null) {
            throw new RuntimeException("Employee not created");
        }

        return createdEmployee;
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Employee updatedEmployee = employeeDao.updateEmployee(id, employee);

        if (updatedEmployee == null) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }

        return updatedEmployee;
    }

    public void deleteEmployeeById(Long id) {
        int rowsDeleted = employeeDao.deleteEmployee(id);
        if(rowsDeleted==0){
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        
    }
}
