package com.example.employeeapi.soap.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PatchDepartmentRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"id", "department"})
public class PatchDepartmentRequest {

    private Long id;
    private DepartmentSoapDto department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DepartmentSoapDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentSoapDto department) {
        this.department = department;
    }
}
