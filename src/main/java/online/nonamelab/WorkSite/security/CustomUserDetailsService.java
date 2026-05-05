package online.nonamelab.WorkSite.security;

import online.nonamelab.WorkSite.user.model.User;
import online.nonamelab.WorkSite.user.repository.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@NullMarked
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(email)
                );

        if (user.isDeleted()) {
            throw new UsernameNotFoundException("User is deleted");
        }

        return new UserPrincipal(user);
    }
}
