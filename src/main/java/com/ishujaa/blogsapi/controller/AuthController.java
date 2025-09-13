package com.ishujaa.blogsapi.controller;

import com.ishujaa.blogsapi.payload.req.LoginRequestDTO;
import com.ishujaa.blogsapi.payload.req.SignupRequestDTO;
import com.ishujaa.blogsapi.payload.res.LoginResponseDTO;
import com.ishujaa.blogsapi.payload.res.SignupResponseDTO;
import com.ishujaa.blogsapi.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO){
        return ResponseEntity.ok(authService.signup(signupRequestDTO));
    }

}
