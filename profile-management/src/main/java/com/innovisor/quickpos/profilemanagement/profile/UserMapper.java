package com.innovisor.quickpos.profilemanagement.profile;

public class UserMapper {
    public static UserRequest toRequest(User user) {
        return new UserRequest(
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public static User toEntity(UserRequest userRequest){
        return User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .build();
    }
}
