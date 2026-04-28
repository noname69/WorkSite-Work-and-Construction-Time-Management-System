package online.nonamelab.WorkSite.service;

import online.nonamelab.WorkSite.dto.CreateUserRequest;
import online.nonamelab.WorkSite.dto.UpdateUserRequest;
import online.nonamelab.WorkSite.dto.UserMapper;
import online.nonamelab.WorkSite.dto.UserResponse;
import online.nonamelab.WorkSite.exception.DuplicateEmailException;
import online.nonamelab.WorkSite.exception.UserNotFoundException;
import online.nonamelab.WorkSite.model.Role;
import online.nonamelab.WorkSite.model.User;
import online.nonamelab.WorkSite.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public List<UserResponse> getAll() {
        return UserMapper.toResponseList(userRepository.findAll());
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse create(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException(request.email());
        }

        User user = UserMapper.toUser(request);
        user.setPassword(encoder.encode(request.password()));

        // enforce business rule
//        if (request.role() == Role.ADMIN) {
//            throw new RuntimeException("Only super admin can assign ADMIN role");
//        }

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if(userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new DuplicateEmailException(request.email());
        }

        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(request.role());

        User userUpdated = userRepository.save(user);

        return UserMapper.toResponse(userUpdated);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }
}
