package ru.spbpu.collections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.RefreshToken;
import ru.spbpu.collections.exceptions.TokenRefreshException;
import ru.spbpu.collections.repositories.RefreshTokenRepository;
import ru.spbpu.collections.repositories.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static ru.spbpu.collections.utils.DateTimeUtils.now;
import static ru.spbpu.collections.utils.DateTimeUtils.toTimestamp;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${spring.application.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public Optional<RefreshToken> findByToken(final String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public RefreshToken createRefreshToken(final Long userId) {

        final RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("Пользователь не найден с id `" + userId + '`'))
        );
        refreshToken.setExpiryDate(Timestamp.from(Instant.now().plusMillis(refreshTokenDurationMs)));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(final RefreshToken token) {

        if (token.getExpiryDate().compareTo(toTimestamp(now())) < 0) {

            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(
                    token.getToken(),
                    "Срок действия токена истек. Пожалуйста, отправьте новый запрос на подпись"
            );
        }

        return token;
    }

    @Transactional
    public void deleteByUserId(final Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Transactional
    public void deleteExpiredTokensForUser(final Long userId) {
        refreshTokenRepository.deleteExpiredTokenByUser(userId);
    }

}
