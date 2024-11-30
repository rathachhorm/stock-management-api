package com.innovisor.quickpos.profilemanagement.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
    private Roles roleName;
    private String description;
}