package com.bezkoder.springjwt.payload;

public enum ResponseCode {
    SUCCESS("0000", "Operation successful"),
    PASSWORD_CHANGED_SUCCESSFULLY("0001", "The password has changed successfully"),

    // Validation Errors (1000 series)
    INVALID_REQUEST("1001", "Invalid request"),
    EMAIL_ALREADY_EXISTS("1002", "Email is already taken"),
    USERNAME_ALREADY_EXISTS("1003", "Username is already taken"),
    FIELD_MISSING("1004", "Required field is missing"),
    PASSWORD_IS_SAME_AS_BEFORE("1005", "Your password is same as before"),

    // Authentication and Authorization (2000 series)
    UNAUTHORIZED("2001", "Unauthorized access"),
    FORBIDDEN("2002", "Access denied"),
    TOKEN_EXPIRED("2003", "JWT token has expired"),
    INVALID_SIGNATURE("2004", "Invalid JWT signature"),
    ACCOUNT_LOCKED("2005", "Account is locked"),
    MALFORMED_SIGNATURE("2006", "Malformed JWT signature"),
    INVALID_USERNAME_PASSWORD_CHANGE_REQUEST("2007", "You are not authorize to changing password for requested username"),
    INVALID_TOKEN("2008", "Your token is invalid "),

    // Resource Errors (3000 series)
    USER_NOT_FOUND("3001", "User not found"),
    RESOURCE_NOT_FOUND("3002", "Resource not found"),
    RESOURCE_ALREADY_EXISTS("3003", "Resource already exists"),

    // Server Errors (5000 series)
    INTERNAL_SERVER_ERROR("5000", "Internal server error"),
    SERVICE_UNAVAILABLE("5001", "Service temporarily unavailable"),
    ACCESS_DENIED("5002", "You dont have permission");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
