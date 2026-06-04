package com.example.employeeapi.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PatchDepartmentResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"department"})
public class PatchDepartmentResponse {

    private DepartmentSoapDto department;

    public DepartmentSoapDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentSoapDto department) {
        this.department = department;
    }
}
