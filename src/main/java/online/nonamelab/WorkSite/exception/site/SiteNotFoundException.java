package online.nonamelab.WorkSite.exception.site;

import online.nonamelab.WorkSite.exception.core.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SiteNotFoundException extends NotFoundException {
        public SiteNotFoundException(Long id) {
            super("Construction Site with id " + id + " was not found.");
        }


}
