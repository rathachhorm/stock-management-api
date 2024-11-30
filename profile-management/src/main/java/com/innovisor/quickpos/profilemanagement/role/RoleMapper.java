package com.innovisor.quickpos.profilemanagement.role;

public final class RoleMapper {
    public static RoleRequest toRequest(Role role) {
        return new RoleRequest(
                role.getRoleName(),
                role.getDescription()
        );
    }

    public static RoleResponse toResponse(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getRoleName(),
                role.getDescription()
        );
    }

    public static Role toEntity(RoleRequest roleRequest) {
        return Role.builder()
                .roleName(roleRequest.getRoleName())
                .description(roleRequest.getDescription())
                .build();
    }
}
