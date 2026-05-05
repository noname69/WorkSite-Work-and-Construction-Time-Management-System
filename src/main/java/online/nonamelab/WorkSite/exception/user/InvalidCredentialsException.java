package online.nonamelab.WorkSite.exception.user;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid email or password");
    }
}
