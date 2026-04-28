package online.nonamelab.WorkSite.dto;

import online.nonamelab.WorkSite.model.User;

public class UserMapper {
    public static User toUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRole(request.role());
        return user;
    }
}
