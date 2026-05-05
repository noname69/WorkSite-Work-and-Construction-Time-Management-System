package online.nonamelab.WorkSite.user.mapper;

import online.nonamelab.WorkSite.user.model.User;
import online.nonamelab.WorkSite.user.dto.CreateUserRequest;
import online.nonamelab.WorkSite.user.dto.UpdateUserRequest;
import online.nonamelab.WorkSite.user.dto.UserResponse;

import java.util.List;

public class UserMapper {
    // CREATE
    public static User toUser(CreateUserRequest request) {
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRole(request.role());
        user.setPhoneNumber(request.phoneNumber());
        return user;
    }

    // UPDATE
//    public static User toUser(UpdateUserRequest request, Long id) {
//        User user = new User();
//        user.setId(user.getId());
//        user.setFirstName(request.firstName());
//        user.setLastName(request.lastName());
//        user.setEmail(request.email());
//        user.setRole(request.role());
//        user.setPhoneNumber(request.phoneNumber());
//        return user;
//    }
    public static void updateUser(User user, UpdateUserRequest request) {

        if (request.firstName() != null) {
            user.setFirstName(request.firstName());
        }

        if (request.lastName() != null) {
            user.setLastName(request.lastName());
        }

        if (request.email() != null) {
            user.setEmail(request.email());
        }

        if (request.role() != null) {
            user.setRole(request.role());
        }

        if (request.phoneNumber() != null) {
            user.setPhoneNumber(request.phoneNumber());
        }
    }

    // SINGLE RESPONSE
    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }

    // LIST RESPONSE
    public static List<UserResponse> toResponseList(List<User> users) {
        return users.stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}
