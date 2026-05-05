package online.nonamelab.WorkSite.user.controller;

import jakarta.validation.Valid;
import online.nonamelab.WorkSite.model.Role;
import online.nonamelab.WorkSite.user.dto.*;
import online.nonamelab.WorkSite.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<UserResponse> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) Boolean deleted,
            Pageable pageable
    ) {
        UserFilter filter = new UserFilter();
        filter.setSearch(search);
        filter.setRole(role);
        filter.setDeleted(deleted);

        return userService.getAll(filter, pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','WORKER')")
//  @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    // create User
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> create(
            @RequestBody @Valid CreateUserRequest request) {

        UserResponse response = userService.create(request);

        return ResponseEntity.ok(response);
    }

    // update User
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/{id}")
    public UserResponse update(
            @PathVariable long id,
            @RequestBody @Valid UpdateUserRequest request
            ) {
        return  userService.update(id, request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','WORKER')")
    @GetMapping("/me")
    public UserResponse getMe() {
        return userService.getMe();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/restore")
    public ResponseEntity<Void> restore(@PathVariable Long id) {
        userService.restore(id);
        return ResponseEntity.noContent().build();
    }

    // update ME
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','WORKER')")
    @PatchMapping("/me")
    public UserResponse updateMe(@RequestBody @Valid UpdateMeRequest request) {
        return userService.updateMe(request);
    }
}
