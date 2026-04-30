package online.nonamelab.WorkSite.exception.user;

import online.nonamelab.WorkSite.exception.core.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super("User with id " + id + " was not found.");
    }

    public UserNotFoundException(String email) {
        super("User with email " + email + " was not found.");
    }
}
