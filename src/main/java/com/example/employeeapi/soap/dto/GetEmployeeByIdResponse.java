package com.example.employeeapi.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "GetEmployeeByIdResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"employee"})
public class GetEmployeeByIdResponse {

    private EmployeeSoapDto employee;

    public EmployeeSoapDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeSoapDto employee) {
        this.employee = employee;
    }
}
