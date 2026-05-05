package online.nonamelab.WorkSite.user.service.impl;

import jakarta.transaction.Transactional;
import online.nonamelab.WorkSite.exception.BusinessException;
import online.nonamelab.WorkSite.exception.user.DuplicateEmailException;
import online.nonamelab.WorkSite.exception.user.UserNotFoundException;
import online.nonamelab.WorkSite.user.mapper.UserMapper;
import online.nonamelab.WorkSite.model.Role;
import online.nonamelab.WorkSite.user.model.User;
import online.nonamelab.WorkSite.security.SecurityUtils;
import online.nonamelab.WorkSite.security.UserPrincipal;
import online.nonamelab.WorkSite.user.dto.CreateUserRequest;
import online.nonamelab.WorkSite.user.dto.UpdateMeRequest;
import online.nonamelab.WorkSite.user.dto.UpdateUserRequest;
import online.nonamelab.WorkSite.user.dto.UserResponse;
import online.nonamelab.WorkSite.user.repository.UserRepository;
import online.nonamelab.WorkSite.user.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final SecurityUtils securityUtils;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, SecurityUtils securityUtils) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.securityUtils = securityUtils;
    }

    @Override
    public List<UserResponse> getAll() {
        UserPrincipal current = securityUtils.getCurrentUser();

        List<User> users = userRepository.findAll();

        List<User> filtered = users.stream()
                .filter(user -> current.getRole() == Role.ADMIN
                        || user.getRole() != Role.ADMIN)
                .toList();

        return UserMapper.toResponseList(filtered);
    }

    @Override
    public UserResponse getById(Long id) {

        UserPrincipal current = securityUtils.getCurrentUser();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (user.getRole() == Role.ADMIN
                && current.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("You cannot access admin users");
        }

        return UserMapper.toResponse(user);
    }

    public UserResponse getMe() {
        UserPrincipal currentUser = securityUtils.getCurrentUser();

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(currentUser.getId()));

        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse create(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException(request.email());
        }

        User user = UserMapper.toUser(request);
        user.setPassword(encoder.encode(request.password()));

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateMe(UpdateMeRequest request) {
        UserPrincipal currentUser = securityUtils.getCurrentUser();

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(currentUser.getId()));

        if (userRepository.existsByEmailAndIdNot(request.email(), user.getId())) {
            throw new DuplicateEmailException(request.email());
        }

        user.setName(request.name());
        user.setEmail(request.email());

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {

        UserPrincipal currentUser = securityUtils.getCurrentUser();

        if (currentUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Only ADMIN can update users");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (request.email() != null &&
                userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new DuplicateEmailException(request.email());
        }

        if (request.name() != null) {
            user.setName(request.name());
        }

        if (request.email() != null) {
            user.setEmail(request.email());
        }

        if (request.role() != null) {
            user.setRole(request.role());
        }

        return UserMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        UserPrincipal currentUser = securityUtils.getCurrentUser();

        if (currentUser.getId().equals(id)) {
            throw new BusinessException("You cannot delete yourself");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (user.isDeleted()) {
            throw new BusinessException("User already deleted");
        }

        user.setDeleted(true);
    }

    @Transactional
    @Override
    public void restore(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!user.isDeleted()) {
            throw new BusinessException("User is not deleted");
        }

        user.setDeleted(false);
    }
}
