package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.user.UserService;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.user.UserStatus;
import com.orcafacil.api.interfaceadapter.request.user.UserRequest;
import com.orcafacil.api.interfaceadapter.request.user.UserUpdateRequest;
import com.orcafacil.api.interfaceadapter.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> create(@RequestBody UserRequest request) {
        User created = userService.create(request);
        return ResponseEntity.status(201)
                .body(new ApiResponse<>(true, "Usuário criado com sucesso.", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> update(@PathVariable Integer id, @RequestBody UserUpdateRequest request) {
        User updated = userService.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuário atualizado com sucesso.", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuário deletado com sucesso.", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> findById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "Usuário encontrado.", user)))
                .orElse(ResponseEntity.status(404)
                        .body(new ApiResponse<>(false, "Usuário não encontrado.", null)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuários listados com sucesso.", users));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<User>> findByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "Usuário encontrado por e-mail.", user)))
                .orElse(ResponseEntity.status(404)
                        .body(new ApiResponse<>(false, "Usuário não encontrado com esse e-mail.", null)));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ApiResponse<User>> findByCpf(@PathVariable String cpf) {
        return userService.findByCpf(cpf)
                .map(user -> ResponseEntity.ok(new ApiResponse<>(true, "Usuário encontrado por CPF.", user)))
                .orElse(ResponseEntity.status(404)
                        .body(new ApiResponse<>(false, "Usuário não encontrado com esse CPF.", null)));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<User>>> findByType(@PathVariable String type) {
        List<User> users = userService.findByType(type);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuários encontrados por tipo.", users));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<User>>> findByStatus(@PathVariable UserStatus status) {
        List<User> users = userService.findByStatus(status);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuários encontrados por status.", users));
    }
}
