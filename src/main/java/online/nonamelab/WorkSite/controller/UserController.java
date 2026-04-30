package online.nonamelab.WorkSite.controller;

import jakarta.validation.Valid;
import online.nonamelab.WorkSite.dto.CreateUserRequest;
import online.nonamelab.WorkSite.dto.UpdateMeRequest;
import online.nonamelab.WorkSite.dto.UpdateUserRequest;
import online.nonamelab.WorkSite.dto.UserResponse;
import online.nonamelab.WorkSite.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','WORKER')")
    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','WORKER')")
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> create(
            @RequestBody @Valid CreateUserRequest request) {

        UserResponse response = userService.create(request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','WORKER')")
    @PutMapping("/{id}")
    public UserResponse update(
            @PathVariable long id,
            @RequestBody @Valid UpdateUserRequest request
            ) {
        return  userService.update(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','WORKER')")
    @GetMapping("/me")
    public UserResponse getMe() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        return userService.getMe();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','WORKER')")
    @PutMapping("/me")
    public UserResponse updateMe(@RequestBody @Valid UpdateMeRequest request) {
        return userService.updateMe(request);
    }
}
