package com.flowbit.model;

import jakarta.persistence.*;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String projectDetails;
    private double payment;
    private String status = "PENDING";

    // 🔥 ASSIGNED EMPLOYEE
    private Long assignedEmployeeId;

    // GETTERS & SETTERS

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProjectDetails() { return projectDetails; }
    public void setProjectDetails(String projectDetails) { this.projectDetails = projectDetails; }

    public double getPayment() { return payment; }
    public void setPayment(double payment) { this.payment = payment; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getAssignedEmployeeId() { return assignedEmployeeId; }
    public void setAssignedEmployeeId(Long assignedEmployeeId) {
        this.assignedEmployeeId = assignedEmployeeId;
    }
}