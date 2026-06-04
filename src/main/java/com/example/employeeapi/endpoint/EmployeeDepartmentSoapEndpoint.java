package com.example.employeeapi.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.employeeapi.model.Department;
import com.example.employeeapi.model.Employee;
import com.example.employeeapi.service.DepartmentService;
import com.example.employeeapi.service.EmployeeService;
import com.example.employeeapi.soap.dto.EmployeeDepartmentSoapDto;
import com.example.employeeapi.soap.dto.EmployeeSoapDto;
import com.example.employeeapi.soap.dto.GetEmployeeDepartmentByIdRequest;
import com.example.employeeapi.soap.dto.GetEmployeeDepartmentByIdResponse;

@Endpoint
public class EmployeeDepartmentSoapEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/employeeapi/ws";

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeDepartmentSoapEndpoint(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetEmployeeDepartmentByIdRequest")
    @ResponsePayload
    public GetEmployeeDepartmentByIdResponse getEmployeeById(
            @RequestPayload GetEmployeeDepartmentByIdRequest request) {
        Long requestId = request.getId();
        Employee foundEmployee = employeeService.getEmployeeById(requestId);
        Department foundDepartment = departmentService.getDepartmentById(foundEmployee.getDepartmentId());

        EmployeeSoapDto responseEmployee = new EmployeeSoapDto();
        responseEmployee.setId(foundEmployee.getId());
        responseEmployee.setDepartmentId(foundEmployee.getDepartmentId());
        responseEmployee.setFirstName(foundEmployee.getFirstName());
        responseEmployee.setLastName(foundEmployee.getLastName());
        responseEmployee.setEmail(foundEmployee.getEmail());
        responseEmployee.setPhone(foundEmployee.getPhone());
        responseEmployee.setJobTitle(foundEmployee.getJobTitle());
        responseEmployee.setSalary(foundEmployee.getSalary());

        EmployeeDepartmentSoapDto employeeDepartment = new EmployeeDepartmentSoapDto();
        employeeDepartment.setEmployee(responseEmployee);
        employeeDepartment.setDepartmentName(foundDepartment.getDepartmentName());

        GetEmployeeDepartmentByIdResponse response = new GetEmployeeDepartmentByIdResponse();
        response.setEmployeeDepartment(employeeDepartment);
        return response;
    }
}
