package com.falcon.cms.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String ORGANIZER = "ROLE_ORGANIZER";

    public static final String AUTHOR = "ROLE_AUTHOR";

    public static final String PARTICIPANT = "ROLE_PARTICIPANT";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String USER = "ROLE";

    private AuthoritiesConstants() {
    }
}
