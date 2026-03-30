package com.flowbit.model;

import jakarta.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String role; // ADMIN / EMPLOYEE

    private int rating = 0;
    private int tasksCompleted = 0;
    private int attendanceDays = 0;
    private double salary = 0;

    private String status = "ACTIVE";

    // 🔥 Salary calculation
    public void calculateSalary() {
        this.salary = (tasksCompleted * 50) + (attendanceDays * 20) + (rating * 100);
    }

    // GETTERS & SETTERS

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public int getTasksCompleted() { return tasksCompleted; }
    public void setTasksCompleted(int tasksCompleted) { this.tasksCompleted = tasksCompleted; }

    public int getAttendanceDays() { return attendanceDays; }
    public void setAttendanceDays(int attendanceDays) { this.attendanceDays = attendanceDays; }

    public double getSalary() { return salary; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}