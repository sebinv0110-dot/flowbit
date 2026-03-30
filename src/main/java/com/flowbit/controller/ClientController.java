package com.flowbit.controller;

import com.flowbit.model.Client;
import com.flowbit.service.ClientService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@CrossOrigin("*")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    // REGISTER
    @PostMapping("/register")
    public Client register(@RequestBody Client client) {
        return service.register(client);
    }

    // LOGIN
    @PostMapping("/login")
    public Client login(@RequestBody Client client) {
        return service.login(client);
    }

    // 🔥 CREATE PROJECT
    @PutMapping("/project/{id}")
    public Client createProject(@PathVariable Long id,
                                @RequestBody Client data) {
        return service.createProject(id, data);
    }

    // 🔥 ASSIGN EMPLOYEE (ADMIN)
    @PutMapping("/assign/{clientId}/{empId}")
    public Client assignEmployee(@PathVariable Long clientId,
                                 @PathVariable Long empId) {
        return service.assignEmployee(clientId, empId);
    }

    // GET ALL
    @GetMapping("/all")
    public List<Client> getAll() {
        return service.getAll();
    }
}