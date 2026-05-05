package online.nonamelab.WorkSite.user.service;

import online.nonamelab.WorkSite.user.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<UserResponse> getAll(UserFilter filter, Pageable pageable);

    UserResponse getById(Long id);

    UserResponse getMe();

    UserResponse create(CreateUserRequest request);

    UserResponse updateMe(UpdateMeRequest request);

    UserResponse update(Long id, UpdateUserRequest request);

    void delete(Long id);

    void restore(Long id);

}
