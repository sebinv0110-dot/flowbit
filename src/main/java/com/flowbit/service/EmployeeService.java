package com.flowbit.service;

import com.flowbit.model.Employee;
import com.flowbit.repository.EmployeeRepository;
import com.flowbit.security.JwtUtil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public EmployeeService(EmployeeRepository repo,
                           BCryptPasswordEncoder encoder,
                           JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    // REGISTER
    public Employee register(Employee emp) {

        repo.findByEmail(emp.getEmail()).ifPresent(e -> {
            throw new RuntimeException("Email already exists!");
        });

        emp.setPassword(encoder.encode(emp.getPassword()));

        if (repo.count() == 0) {
            emp.setRole("ADMIN");
        } else {
            emp.setRole("EMPLOYEE");
        }

        return repo.save(emp);
    }

    // LOGIN
    public String login(Employee emp) {

        Employee existing = repo.findByEmail(emp.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(emp.getPassword(), existing.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(existing.getEmail() + ":" + existing.getRole());
    }

    // GET ALL
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    // ADMIN UPDATE
    public Employee updateEmployee(Long id, Employee emp) {

        Employee existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setRating(emp.getRating());
        existing.setTasksCompleted(emp.getTasksCompleted());
        existing.setAttendanceDays(emp.getAttendanceDays());

        existing.calculateSalary();

        return repo.save(existing);
    }

    // 🔥 GET PROFILE (NEW)
    public Employee getByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 🔥 ATTENDANCE (NEW)
    public Employee markAttendance(Long id) {

        Employee emp = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        emp.setAttendanceDays(emp.getAttendanceDays() + 1);

        emp.calculateSalary();

        return repo.save(emp);
    }
}