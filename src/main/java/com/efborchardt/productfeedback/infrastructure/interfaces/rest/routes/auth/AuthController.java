package com.efborchardt.productfeedback.infrastructure.interfaces.rest.routes.auth;

import com.efborchardt.productfeedback.infrastructure.interfaces.rest.security.TokenService;
import com.efborchardt.productfeedback.infrastructure.persistence.user.model.UserEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO authenticationRequest) throws Exception {

        final UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                authenticationRequest.username(),
                authenticationRequest.password()
        );
        final Authentication auth = this.authenticationManager.authenticate(usernamePassword);
        final String token = this.tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
