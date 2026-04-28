package online.nonamelab.WorkSite.service;

import online.nonamelab.WorkSite.dto.CreateUserRequest;
import online.nonamelab.WorkSite.dto.UpdateUserRequest;
import online.nonamelab.WorkSite.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAll();

    UserResponse getById(Long id);

    UserResponse create(CreateUserRequest request);

    UserResponse update(Long id, UpdateUserRequest request);

    void delete(Long id);

}
