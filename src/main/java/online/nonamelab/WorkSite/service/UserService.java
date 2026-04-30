package online.nonamelab.WorkSite.service;

import online.nonamelab.WorkSite.dto.CreateUserRequest;
import online.nonamelab.WorkSite.dto.UpdateMeRequest;
import online.nonamelab.WorkSite.dto.UpdateUserRequest;
import online.nonamelab.WorkSite.dto.UserResponse;

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
