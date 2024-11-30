package com.innovisor.quickpos.profilemanagement.group;

import com.innovisor.quickpos.profilemanagement.profile.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final UserService userService;

    public void assignGroup(String userId, String groupId) {

        UserResource user = userService.getUser(userId);
        user.joinGroup(groupId);
    }

    public void deleteGroupFromUser(String userId, String groupId) {
        UserResource user = userService.getUser(userId);
        user.leaveGroup(groupId);
    }

}
