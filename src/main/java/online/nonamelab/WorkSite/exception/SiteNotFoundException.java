package online.nonamelab.WorkSite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SiteNotFoundException extends RuntimeException {
        public SiteNotFoundException(Long id) {

            super("Construction Site with id " + id + " was not found.");
        }


}
