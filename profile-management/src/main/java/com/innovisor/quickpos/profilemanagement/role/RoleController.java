package com.innovisor.quickpos.profilemanagement.role;

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
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    @GetMapping
    public ResponseEntity<?> getAllRoles(){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAllRoles());
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleRequest roleRequest){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.createRole(roleRequest));
    }


    @PutMapping("/assign/users/{userId}")
    public ResponseEntity<?> assignRole(
            @PathVariable String userId,
            @RequestParam(name = "roleName") String roleName
    ) {
        roleService.assignRole(userId, roleName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/remove/users/{userId}")
    public ResponseEntity<?> unAssignRole(@PathVariable String userId, @RequestParam String roleName) {
        roleService.unAssignRole(userId, roleName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
