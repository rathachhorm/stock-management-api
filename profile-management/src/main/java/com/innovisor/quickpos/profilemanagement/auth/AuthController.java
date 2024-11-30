package com.innovisor.quickpos.profilemanagement.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequest));
    }

    @PostMapping("/get-token")
    public ResponseEntity<?> getToken(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.status(HttpStatus.OK).body("access_token : " + authService.getToken(authRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.status(HttpStatus.OK).body("refresh_token : " + authService.refreshToken(authRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> refreshToken(@RequestParam String token) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
