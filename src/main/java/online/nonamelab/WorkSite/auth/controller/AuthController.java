package online.nonamelab.WorkSite.auth.controller;

import jakarta.validation.Valid;
import online.nonamelab.WorkSite.auth.service.AuthService;
import online.nonamelab.WorkSite.user.dto.AuthResponse;
import online.nonamelab.WorkSite.user.dto.LoginRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
}
