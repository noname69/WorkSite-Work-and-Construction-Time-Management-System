package online.nonamelab.WorkSite.exception.user;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {

        super("User with email '" + email + "' already exists.");

    }
}
