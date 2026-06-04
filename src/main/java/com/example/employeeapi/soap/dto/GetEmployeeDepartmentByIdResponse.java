package com.example.employeeapi.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "GetEmployeeDepartmentByIdResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"employeeDepartment"})
public class GetEmployeeDepartmentByIdResponse {

    private EmployeeDepartmentSoapDto employeeDepartment;

    public EmployeeDepartmentSoapDto getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(EmployeeDepartmentSoapDto employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }
}
