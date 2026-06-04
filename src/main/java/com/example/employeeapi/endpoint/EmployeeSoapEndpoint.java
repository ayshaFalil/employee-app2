package com.example.employeeapi.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.employeeapi.model.Employee;
import com.example.employeeapi.service.EmployeeService;
import com.example.employeeapi.service.EmployeeService;
import com.example.employeeapi.soap.dto.CreateEmployeeRequest;
import com.example.employeeapi.soap.dto.CreateEmployeeResponse;
import com.example.employeeapi.soap.dto.DeleteEmployeeRequest;
import com.example.employeeapi.soap.dto.DeleteEmployeeResponse;
import com.example.employeeapi.soap.dto.EmployeeSoapDto;
import com.example.employeeapi.soap.dto.GetEmployeeByIdRequest;
import com.example.employeeapi.soap.dto.GetEmployeeByIdResponse;
import com.example.employeeapi.soap.dto.PatchEmployeeRequest;
import com.example.employeeapi.soap.dto.PatchEmployeeResponse;

@Endpoint
public class EmployeeSoapEndpoint {
    
    private static final String NAMESPACE_URI = "http://example.com/employeeapi/ws";
    private final EmployeeService employeeService;

    public EmployeeSoapEndpoint(EmployeeService employeeService){
        this.employeeService = employeeService;

    }
    

    // SOAP endpoint methods use JAXB DTOs so Spring can unmarshal SOAP XML automatically.
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetEmployeeByIdRequest")
    @ResponsePayload
    public GetEmployeeByIdResponse getEmployeeById(@RequestPayload GetEmployeeByIdRequest request) {
       
        Long requestId = request.getId();
        
        Employee foundEmployee = employeeService.getEmployeeById(requestId);
        EmployeeSoapDto responseEmployee = new EmployeeSoapDto();
        responseEmployee.setId(foundEmployee.getId());
        responseEmployee.setDepartmentId(foundEmployee.getDepartmentId());
        responseEmployee.setFirstName(foundEmployee.getFirstName());
        responseEmployee.setLastName(foundEmployee.getLastName());
        responseEmployee.setEmail(foundEmployee.getEmail());
        responseEmployee.setPhone(foundEmployee.getPhone());
        responseEmployee.setJobTitle(foundEmployee.getJobTitle());
        responseEmployee.setSalary(foundEmployee.getSalary());
        
        GetEmployeeByIdResponse response = new GetEmployeeByIdResponse();
        response.setEmployee(responseEmployee);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateEmployeeRequest")
    @ResponsePayload
    public CreateEmployeeResponse createEmployee(@RequestPayload CreateEmployeeRequest request) {
        
        EmployeeSoapDto requestEmployee  = request.getEmployee();
        Employee employee = new Employee();
        employee.setDepartmentId(requestEmployee.getDepartmentId());
        employee.setFirstName(requestEmployee.getFirstName());
        employee.setLastName(requestEmployee.getLastName());
        employee.setEmail(requestEmployee.getEmail());
        employee.setPhone(requestEmployee.getPhone());
        employee.setJobTitle(requestEmployee.getJobTitle());
        employee.setSalary(requestEmployee.getSalary());
    
        Employee createdEmployee = employeeService.createEmployee(employee);
        EmployeeSoapDto responseEmployee = new EmployeeSoapDto();
        responseEmployee.setId(createdEmployee.getId());
        responseEmployee.setDepartmentId(createdEmployee.getDepartmentId());
        responseEmployee.setFirstName(createdEmployee.getFirstName());
        responseEmployee.setLastName(createdEmployee.getLastName());
        responseEmployee.setEmail(createdEmployee.getEmail());
        responseEmployee.setPhone(createdEmployee.getPhone());
        responseEmployee.setJobTitle(createdEmployee.getJobTitle());
        responseEmployee.setSalary(createdEmployee.getSalary());

        CreateEmployeeResponse response = new CreateEmployeeResponse();
        response.setEmployee(responseEmployee);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PatchEmployeeRequest")
    @ResponsePayload
    public PatchEmployeeResponse patchEmployee(@RequestPayload PatchEmployeeRequest request) {

        Long requestId = request.getId();
        EmployeeSoapDto requestEmployee = request.getEmployee();
        Employee employee = new Employee();
        employee.setDepartmentId(requestEmployee.getDepartmentId());
        employee.setFirstName(requestEmployee.getFirstName());
        employee.setLastName(requestEmployee.getLastName());
        employee.setEmail(requestEmployee.getEmail());
        employee.setPhone(requestEmployee.getPhone());
        employee.setJobTitle(requestEmployee.getJobTitle());
        employee.setSalary(requestEmployee.getSalary());

       Employee updatedEmployee = employeeService.updateEmployee(requestId, employee);

       EmployeeSoapDto responseEmployee = new EmployeeSoapDto();
        responseEmployee.setId(updatedEmployee.getId());
        responseEmployee.setDepartmentId(updatedEmployee.getDepartmentId());
        responseEmployee.setFirstName(updatedEmployee.getFirstName());
        responseEmployee.setLastName(updatedEmployee.getLastName());
        responseEmployee.setEmail(updatedEmployee.getEmail());
        responseEmployee.setPhone(updatedEmployee.getPhone());
        responseEmployee.setJobTitle(updatedEmployee.getJobTitle());
        responseEmployee.setSalary(updatedEmployee.getSalary());

        PatchEmployeeResponse response = new PatchEmployeeResponse();
        
       /* if (employee != null) {
            employee.setId(requestId);
        }*/
        response.setEmployee(responseEmployee);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteEmployeeRequest")
    @ResponsePayload
    public DeleteEmployeeResponse deleteEmployee(@RequestPayload DeleteEmployeeRequest request) {
        Long requestId = request.getId();
        employeeService.deleteEmployeeById(requestId);
        DeleteEmployeeResponse response = new DeleteEmployeeResponse();
        response.setMessage("Employee deleted successfully for id: " + requestId);
        return response;
    }
}
