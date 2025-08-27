package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.user.UserService;
import com.orcafacil.api.domain.user.User;
import com.orcafacil.api.domain.user.UserStatus;
import com.orcafacil.api.interfaceadapter.request.user.UserRequest;
import com.orcafacil.api.interfaceadapter.request.user.UserUpdateRequest;
import com.orcafacil.api.interfaceadapter.response.ApiResponse;
import com.orcafacil.api.interfaceadapter.response.UserResponse;
import com.orcafacil.api.infrastructure.security.jwt.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<User>> updateUserStatus(@PathVariable Integer id, @RequestBody Map<String, String> statusUpdate) {
        String newStatus = statusUpdate.get("status");
        if (newStatus == null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "O campo 'status' é obrigatório.", null));
        }
        User updatedUser = userService.updateStatus(id, UserStatus.valueOf(newStatus.toUpperCase()));
        return ResponseEntity.ok(new ApiResponse<>(true, "Status do usuário atualizado com sucesso.", updatedUser));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(HttpServletRequest request) {
        try {
            String token = tokenService.extractTokenFromRequest(request);
            if (token == null) return ResponseEntity.status(401)
                    .body(new ApiResponse<>(false, "Token não encontrado.", null));

            String email = tokenService.getEmailFromToken(token);
            User user = userService.getUserByEmail(email);

            UserResponse userResponse = new UserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getCpf(),
                    user.getUserType(),
                    user.getStatus(),
                    user.getBirthDate()
            );

            return ResponseEntity.ok(new ApiResponse<>(true, "Usuário logado recuperado com sucesso.", userResponse));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, "Erro ao recuperar usuário logado.", null));
        }
    }


}
