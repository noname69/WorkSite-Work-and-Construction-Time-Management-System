package online.nonamelab.WorkSite.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationError(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp,
        List<ApiFieldError> fieldErrors
) {}
