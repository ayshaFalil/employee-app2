package com.example.employeeapi.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PatchEmployeeRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"id", "employee"})
public class PatchEmployeeRequest {

    private Long id;
    private EmployeeSoapDto employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeSoapDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeSoapDto employee) {
        this.employee = employee;
    }
}
