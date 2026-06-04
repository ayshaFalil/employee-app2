package com.example.employeeapi.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.employeeapi.model.Department;
import com.example.employeeapi.service.DepartmentService;
import com.example.employeeapi.soap.dto.CreateDepartmentRequest;
import com.example.employeeapi.soap.dto.CreateDepartmentResponse;
import com.example.employeeapi.soap.dto.DeleteDepartmentRequest;
import com.example.employeeapi.soap.dto.DeleteDepartmentResponse;
import com.example.employeeapi.soap.dto.DepartmentSoapDto;
import com.example.employeeapi.soap.dto.GetDepartmentByIdRequest;
import com.example.employeeapi.soap.dto.GetDepartmentByIdResponse;
import com.example.employeeapi.soap.dto.PatchDepartmentRequest;
import com.example.employeeapi.soap.dto.PatchDepartmentResponse;

@Endpoint
public class DepartmentSoapEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/employeeapi/ws";

    private final DepartmentService departmentService;

    public DepartmentSoapEndpoint(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetDepartmentByIdRequest")
    @ResponsePayload
    public GetDepartmentByIdResponse getDepartmentById(@RequestPayload GetDepartmentByIdRequest request) {
        Long requestId = request.getId();
        Department foundDepartment = departmentService.getDepartmentById(requestId);

        DepartmentSoapDto responseDepartment = new DepartmentSoapDto();
        responseDepartment.setId(foundDepartment.getId());
        responseDepartment.setDepartmentName(foundDepartment.getDepartmentName());

        GetDepartmentByIdResponse response = new GetDepartmentByIdResponse();
        response.setDepartment(responseDepartment);

        return response;
       
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateDepartmentRequest")
    @ResponsePayload
    public CreateDepartmentResponse createDepartment(@RequestPayload CreateDepartmentRequest request) {
        DepartmentSoapDto requestDepartment = request.getDepartment();
        Department department = new Department();
        department.setDepartmentName(requestDepartment.getDepartmentName());

        Department createdDepartment= departmentService.createDepartment(department);

        DepartmentSoapDto responseDepartment = new DepartmentSoapDto();
        responseDepartment.setId(createdDepartment.getId());
        responseDepartment.setDepartmentName(createdDepartment.getDepartmentName());

        CreateDepartmentResponse createDepartmentResponse = new CreateDepartmentResponse();
        createDepartmentResponse.setDepartment(responseDepartment);
        return createDepartmentResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PatchDepartmentRequest")
    @ResponsePayload
    public PatchDepartmentResponse patchDepartment(@RequestPayload PatchDepartmentRequest request) {
        Long requestId = request.getId();
        DepartmentSoapDto requestDepartment = request.getDepartment();

        Department department = new Department();
        department.setDepartmentName(requestDepartment.getDepartmentName());
        Department updatedDepartment = departmentService.updateDepartment(requestId,department);

        DepartmentSoapDto responseDepartment = new DepartmentSoapDto();
        responseDepartment.setId(updatedDepartment.getId());
        responseDepartment.setDepartmentName(updatedDepartment.getDepartmentName());

        PatchDepartmentResponse patchDepartmentResponse = new PatchDepartmentResponse();
        patchDepartmentResponse.setDepartment(responseDepartment);

        return patchDepartmentResponse;
       
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteDepartmentRequest")
    @ResponsePayload
    public DeleteDepartmentResponse deleteDepartment(@RequestPayload DeleteDepartmentRequest request) {
        Long requestId = request.getId();
        departmentService.deleteDepartmentById(requestId);

        DeleteDepartmentResponse deleteDepartmentResponse = new DeleteDepartmentResponse();
        deleteDepartmentResponse.setMessage("Department deleted successfully for id: " + requestId);
        return deleteDepartmentResponse;

    }
}
