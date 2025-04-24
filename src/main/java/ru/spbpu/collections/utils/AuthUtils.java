package ru.spbpu.collections.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.spbpu.collections.model.security.UserDetailsImpl;

import java.util.List;

public final class AuthUtils {

    private static final UserDetailsImpl UNAUTHORIZED_USER = new UserDetailsImpl(0L, null, null, List.of());

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static UserDetailsImpl getAuthUser() {

        final Authentication authentication = getAuthentication();

        if (authentication == null) {
            return UNAUTHORIZED_USER;
        }

        final Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetailsImpl) {
            return (UserDetailsImpl)principal;
        }

        return UNAUTHORIZED_USER;
    }
}
