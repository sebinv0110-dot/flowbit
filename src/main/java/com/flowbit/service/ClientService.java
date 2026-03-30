package com.flowbit.service;

import com.flowbit.model.Client;
import com.flowbit.repository.ClientRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repo;
    private final BCryptPasswordEncoder encoder;

    public ClientService(ClientRepository repo,
                         BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    // REGISTER
    public Client register(Client client) {

        repo.findByEmail(client.getEmail()).ifPresent(c -> {
            throw new RuntimeException("Email already exists!");
        });

        client.setPassword(encoder.encode(client.getPassword()));

        return repo.save(client);
    }

    // LOGIN
    public Client login(Client client) {

        Client existing = repo.findByEmail(client.getEmail())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!encoder.matches(client.getPassword(), existing.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return existing;
    }

    // 🔥 CREATE PROJECT
    public Client createProject(Long id, Client data) {

        Client client = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setProjectDetails(data.getProjectDetails());
        client.setPayment(data.getPayment());
        client.setStatus("CREATED");

        return repo.save(client);
    }

    // 🔥 ASSIGN EMPLOYEE (ADMIN)
    public Client assignEmployee(Long clientId, Long empId) {

        Client client = repo.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setAssignedEmployeeId(empId);
        client.setStatus("ASSIGNED");

        return repo.save(client);
    }

    // GET ALL CLIENTS
    public List<Client> getAll() {
        return repo.findAll();
    }
}