package online.nonamelab.WorkSite.auth.service;

import online.nonamelab.WorkSite.exception.user.InvalidCredentialsException;
import online.nonamelab.WorkSite.user.model.User;
import online.nonamelab.WorkSite.security.JwtService;
import online.nonamelab.WorkSite.user.dto.AuthResponse;
import online.nonamelab.WorkSite.user.dto.LoginRequest;
import online.nonamelab.WorkSite.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;


    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if(!encoder.matches(request.password(), user.getPassword())) {
            throw  new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(
                "Bearer",
                token,
                user.getEmail(),
                user.getRole());
    }
}
