package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.user.UserService;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.user.UserStatus;
import com.orcafacil.api.interfaceadapter.request.user.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserRequest request) {
        User created = userService.create(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Integer id, @RequestBody UserRequest request) {
        User updated = userService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<User> findByCpf(@PathVariable String cpf) {
        Optional<User> user = userService.findByCpf(cpf);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<User>> findByType(@PathVariable String type) {
        return ResponseEntity.ok(userService.findByType(type));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<User>> findByStatus(@PathVariable UserStatus status) {
        return ResponseEntity.ok(userService.findByStatus(status));
    }
}
