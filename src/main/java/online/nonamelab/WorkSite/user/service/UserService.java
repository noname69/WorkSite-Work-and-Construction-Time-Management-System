package online.nonamelab.WorkSite.user.service;

import online.nonamelab.WorkSite.user.dto.CreateUserRequest;
import online.nonamelab.WorkSite.user.dto.UpdateMeRequest;
import online.nonamelab.WorkSite.user.dto.UpdateUserRequest;
import online.nonamelab.WorkSite.user.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAll();

    UserResponse getById(Long id);

    UserResponse getMe();

    UserResponse create(CreateUserRequest request);

    UserResponse updateMe(UpdateMeRequest request);

    UserResponse update(Long id, UpdateUserRequest request);

    void delete(Long id);

}
