package com.innovisor.quickpos.profilemanagement.profile;

import com.innovisor.quickpos.profilemanagement.role.RoleMapper;
import com.innovisor.quickpos.profilemanagement.role.RoleRepository;
import com.innovisor.quickpos.profilemanagement.role.RoleRequest;
import com.innovisor.quickpos.profilemanagement.role.Roles;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final Keycloak keycloak;
    @Value("${app.keycloak.admin.realm}")
    private String realm;

    public void createUser(UserRequest userRequest){

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(userRequest.getFirstName());
        userRepresentation.setLastName(userRequest.getLastName());
        userRepresentation.setUsername(userRequest.getUsername());
        userRepresentation.setEmail(userRequest.getUsername());
        userRepresentation.setEmailVerified(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userRequest.getPassword());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        userRepresentation.setCredentials(List.of(credentialRepresentation));

        UsersResource usersResource = getUserResource();

        Response response = usersResource.create(userRepresentation);

        log.info("Status Code {}", response.getStatus());

        if(!Objects.equals(201, response.getStatus())){
            throw new RuntimeException("Status Code " + response.getStatus());
        }

        log.info("User has be created");

//        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(newUserProfile.username(), true);
//        UserRepresentation userRepresentation1 = userRepresentations.get(0);
//        sendVerificationEmail(userRepresentation1.getId());

    }

    private UsersResource getUserResource() {
        return keycloak.realm(realm).users();
    }

//    public void sendVerificationEmail(String userId) {
//        UsersResource usersResource = getUserResource();
//        usersResource.get(userId).sendVerifyEmail();
//    }

    public void deleteUser(String userId) {
        UsersResource usersResource = getUserResource();
        usersResource.delete(userId);
    }

    public void forgotPassword(String username) {

        UsersResource usersResource =getUserResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
        UserRepresentation userRepresentation1 = userRepresentations.get(0);

        usersResource.get(userRepresentation1.getId()).executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    public UserResource getUser(String userId) {
        UsersResource usersResource = getUserResource();
        return usersResource.get(userId);
    }

    public List<RoleRepresentation> getUserRoles(String userId) {
        return getUser(userId).roles().realmLevel().listAll();
    }

    public List<GroupRepresentation> getUserGroups(String userId) {
        return getUser(userId).groups();
    }

    public void saveAdminRole(UserRequest userRequest, RoleRequest roleRequest) {
        List<User> users = userRepository.searchAllByUsername(userRequest.getUsername());
        if(users == null){
            var user = userRepository.save(UserMapper.toEntity(userRequest));

            roleRequest.setRoleName(Roles.ADMIN);

            var role = roleRepository.save(RoleMapper.toEntity(roleRequest));

            user.getRoles().add(role);

            userRepository.save(user);

        }
    }

}
