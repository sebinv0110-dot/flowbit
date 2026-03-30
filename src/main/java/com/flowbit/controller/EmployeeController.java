package com.flowbit.controller;

import com.flowbit.model.Employee;
import com.flowbit.service.EmployeeService;
import com.flowbit.security.JwtUtil;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService service;
    private final JwtUtil jwtUtil;

    public EmployeeController(EmployeeService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    // REGISTER
    @PostMapping("/register")
    public Employee register(@RequestBody Employee emp) {
        return service.register(emp);
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody Employee emp) {
        return service.login(emp);
    }

    // GET ALL
    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    // ADMIN UPDATE
    @PutMapping("/update/{id}")
    public Employee update(@PathVariable Long id, @RequestBody Employee emp) {
        return service.updateEmployee(id, emp);
    }

    // 🔥 GET PROFILE (JWT BASED)
    @GetMapping("/me")
    public Employee getMyProfile(@RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        return service.getByEmail(email);
    }

    // 🔥 ATTENDANCE
    @PutMapping("/attendance/{id}")
    public Employee markAttendance(@PathVariable Long id) {
        return service.markAttendance(id);
    }
}