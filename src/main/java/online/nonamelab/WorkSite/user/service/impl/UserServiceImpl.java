package online.nonamelab.WorkSite.user.service.impl;

import jakarta.transaction.Transactional;
import online.nonamelab.WorkSite.exception.BusinessException;
import online.nonamelab.WorkSite.exception.user.DuplicateEmailException;
import online.nonamelab.WorkSite.exception.user.UserNotFoundException;
import online.nonamelab.WorkSite.user.dto.*;
import online.nonamelab.WorkSite.user.mapper.UserMapper;
import online.nonamelab.WorkSite.model.Role;
import online.nonamelab.WorkSite.user.model.User;
import online.nonamelab.WorkSite.security.SecurityUtils;
import online.nonamelab.WorkSite.security.UserPrincipal;
import online.nonamelab.WorkSite.user.repository.UserRepository;
import online.nonamelab.WorkSite.user.service.UserService;
import online.nonamelab.WorkSite.user.specifications.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<UserResponse> getAll(UserFilter filter, Pageable pageable) {
        UserPrincipal current = securityUtils.getCurrentUser();

        if (current.getRole() != Role.ADMIN) {
            filter.setRole(Role.WORKER);
            filter.setDeleted(false);
        }

        Specification<User> spec = UserSpecification.filter(filter);

        return userRepository.findAll(spec, pageable)
                .map(UserMapper::toResponse);
    }

    // get User by ID
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

    // get info about ME
    public UserResponse getMe() {
        UserPrincipal currentUser = securityUtils.getCurrentUser();

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(currentUser.getId()));

        return UserMapper.toResponse(user);
    }

    // create USER
    @Override
    public UserResponse create(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException(request.email());
        }

        User user = UserMapper.toUser(request);
        user.setPassword(encoder.encode(request.password()));

        return UserMapper.toResponse(userRepository.save(user));
    }

    // update ME
    @Override
    public UserResponse updateMe(UpdateMeRequest request) {
        UserPrincipal currentUser = securityUtils.getCurrentUser();

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(currentUser.getId()));

        if (userRepository.existsByEmailAndIdNot(request.email(), user.getId())) {
            throw new DuplicateEmailException(request.email());
        }


        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());

        return UserMapper.toResponse(userRepository.save(user));
    }

    // update User
    @Override
    public UserResponse update(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (request.email() != null &&
                userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new DuplicateEmailException(request.email());
        }

        UserMapper.updateUser(user, request);

        return UserMapper.toResponse(userRepository.save(user));
    }

    // soft delete User
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

    // restore User if soft deleted
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
