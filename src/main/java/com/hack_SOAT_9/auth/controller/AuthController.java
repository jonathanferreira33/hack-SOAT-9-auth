package com.hack_SOAT_9.auth.controller;

import com.hack_SOAT_9.auth.dto.request.LoginRequestDTO;
import com.hack_SOAT_9.auth.dto.request.RegisterRequestDTO;
import com.hack_SOAT_9.auth.dto.response.LoginResponseDTO;
import com.hack_SOAT_9.auth.entity.RegisterEntity;
import com.hack_SOAT_9.auth.infra.security.TokenService;
import com.hack_SOAT_9.auth.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        var emaill = body.email();
        var pass = body.email();
        RegisterEntity user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(body.password(), user.getPassword()))
            return ResponseEntity.badRequest().build();

        String token = this.service.generateToken(user);

        return ResponseEntity.ok(
                new LoginResponseDTO(user.getName(), token)
        );
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<RegisterEntity> user = this.repository.findByEmail(body.email());

        if(user.isPresent())
            return ResponseEntity.badRequest().build();

        RegisterEntity newUser = new RegisterEntity();
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setName(body.firstName());
        newUser.setSurname(body.surname());
        newUser.setEmail(body.email());
        newUser.setUsername(RegisterEntity.createUsername(body.firstName(), body.surname()));
        this.repository.save(newUser);

        String token = this.service.generateToken(newUser);

        return ResponseEntity.ok(
                new LoginResponseDTO(token, newUser.getName())
        );
    }
}

