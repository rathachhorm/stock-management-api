package com.innovisor.quickpos.profilemanagement.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @PutMapping("/{id}/send-verification-email")
//    public ResponseEntity<?> sendVerificationEmail(@PathVariable(name = "id") String id) {
//        userService.sendVerificationEmail(id);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String username) {
        userService.forgotPassword(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<?> getUserRoles(@PathVariable String id) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserRoles(id));
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<?> getUserGroups(@PathVariable String id) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserGroups(id));
    }

}
