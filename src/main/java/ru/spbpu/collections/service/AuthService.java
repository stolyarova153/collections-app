package ru.spbpu.collections.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.spbpu.collections.config.security.utils.JwtUtils;
import ru.spbpu.collections.entities.RefreshToken;
import ru.spbpu.collections.exceptions.TokenRefreshException;
import ru.spbpu.collections.model.request.LoginRequest;
import ru.spbpu.collections.model.request.TokenRefreshRequest;
import ru.spbpu.collections.model.response.JwtResponse;
import ru.spbpu.collections.model.response.TokenRefreshResponse;
import ru.spbpu.collections.model.security.UserDetailsImpl;

import java.util.List;
import java.util.stream.Collectors;

import static ru.spbpu.collections.utils.AuthUtils.getAuthUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;

    public JwtResponse login(final LoginRequest loginRequest) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        final String jwt = jwtUtils.generateJwtToken(userDetails);
        final List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        final RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        refreshTokenService.deleteExpiredTokensForUser(userDetails.getId());

        log.info("user {} has been authenticated", userDetails.getEmail());

        return new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    public TokenRefreshResponse refreshToken(final TokenRefreshRequest request) {

        try {
            final String refreshToken = request.getRefreshToken();
            return refreshTokenService.findByToken(refreshToken)
                                      .map(refreshTokenService::verifyExpiration)
                                      .map(RefreshToken::getUser)
                                      .map(user -> new TokenRefreshResponse(
                                              jwtUtils.generateTokenFromEmail(user.getEmail()), refreshToken)
                                      )
                                      .orElseThrow(() -> new TokenRefreshException(
                                              refreshToken, "В системе нет данного Refresh Token"
                                      ));
        } catch (final TokenRefreshException e) {
            throw e;
        } catch (final Exception e) {
            throw new TokenRefreshException("Error during processing token refresh: " + e.getMessage());
        }
    }

    public void logoutUser() {

        final UserDetailsImpl authUser = getAuthUser();
        refreshTokenService.deleteExpiredTokensForUser(authUser.getId());
        log.info("logout for user {}", authUser.getEmail());
    }
}