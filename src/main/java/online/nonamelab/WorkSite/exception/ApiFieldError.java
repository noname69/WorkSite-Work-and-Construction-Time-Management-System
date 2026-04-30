package online.nonamelab.WorkSite.exception;


public record ApiFieldError(
        String field,
        String message
) {}
