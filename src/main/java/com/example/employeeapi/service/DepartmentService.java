package com.example.employeeapi.service;

import com.example.employeeapi.dao.DepartmentDao;
import com.example.employeeapi.exception.EmployeeNotFoundException;
import com.example.employeeapi.model.Department;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentDao departmentDao;

    public DepartmentService(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public Department getDepartmentById(Long id) {
        Department department = departmentDao.findById(id);

        if (department == null) {
            throw new EmployeeNotFoundException("Department not found with id: " + id);
        }

        return department;
    }

    public Department createDepartment(Department department) {
        Department createdDepartment = departmentDao.addDepartment(department);

        if (createdDepartment == null) {
            throw new RuntimeException("Department not created");
        }

        return createdDepartment;
    }

    public Department updateDepartment(Long id, Department department) {
        Department updatedDepartment = departmentDao.updateDepartment(id, department);

        if (updatedDepartment == null) {
            throw new EmployeeNotFoundException("Department not found with id: " + id);
        }

        return updatedDepartment;
    }

    public void deleteDepartmentById(Long id) {
        int rowsDeleted = departmentDao.deleteDepartment(id);
        if(rowsDeleted==0){
            throw new EmployeeNotFoundException("Department not found with id: " + id);
        }
        
    }

}
