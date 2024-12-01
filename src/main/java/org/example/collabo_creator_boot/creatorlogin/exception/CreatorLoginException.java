package org.example.collabo_creator_boot.creatorlogin.exception;

public enum CreatorLoginException {
    BAD_AUTH(900, "ID/PW incorrect"),
    TOKEN_NOT_ENOUGH(901, "AccessToken or RefreshToken is NULL"),
    ACCESSTOKEN_TOO_SHORT(902, "Access Token too short"),
    REQUIRE_SIGN_IN(903, "Require sign in"),
    ACCESSTOKEN_EXPIRED(904, "Access Token just Expired!!!"),
    REFRESHTOKEN_EXPIRED(905, "Refresh Token just Expired!!!");

    private final int status;
    private final String message;

    CreatorLoginException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public CreatorLoginTaskException getException() {
        return new CreatorLoginTaskException(status, message);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}