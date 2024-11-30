package com.innovisor.quickpos.profilemanagement.role;


import com.innovisor.quickpos.profilemanagement.profile.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

   private final UserService userService;

    private final Keycloak keycloak;
    @Value("${app.keycloak.admin.realm}")
    private String realm;

    public void assignRole(String userId, String roleName){
       UserResource user = userService.getUser(userId);
       RolesResource rolesResource = getRolesResource();
       RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
       user.roles().realmLevel().add(Collections.singletonList(representation));
   }

    private RolesResource getRolesResource() {
       return keycloak.realm(realm).roles();
   }

    public void unAssignRole(String userId, String roleName) {
        UserResource user = userService.getUser(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation representation = rolesResource.get(roleName).toRepresentation();
        user.roles().realmLevel().remove(Collections.singletonList(representation));
    }

    public RoleRequest createRole(RoleRequest role) {

        if(getAllRoles().contains(role.getRoleName().name())){
            throw new RuntimeException("Role is already exit");
        }

        RoleRepresentation roleRep = new RoleRepresentation(role.getRoleName().name(), role.getDescription(), false);
        keycloak.realm(realm)
                .roles()
                .create(roleRep);

       return role;
    }

    public List<String> getAllRoles(){
        RoleRepresentation roles = keycloak
                .realm(realm)
                .roles()
                .list()
                .getFirst();
        List<String> availableRoles = keycloak
                .realm(realm)
                .roles()
                .list()
                .stream()
                .map(RoleRepresentation::getName)
                .collect(Collectors.toList());
        return availableRoles;
    }

}
