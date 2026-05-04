package online.nonamelab.WorkSite.exception.user;

import online.nonamelab.WorkSite.exception.core.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ManagerNotFoundException extends NotFoundException {
    public ManagerNotFoundException(Long id) {
        super("Manager with id " + id + " was not found.");
    }}

