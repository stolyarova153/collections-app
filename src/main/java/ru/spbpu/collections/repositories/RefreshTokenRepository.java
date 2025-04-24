package ru.spbpu.collections.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Transactional(readOnly = true)
    Optional<RefreshToken> findByToken(final String token);

    @Modifying
    @Query(value = "DELETE FROM refresh_tokens WHERE \"user\" = :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") final Long userId);

    @Modifying
    @Query(
            value = "DELETE FROM refresh_tokens WHERE \"user\" = :userId AND expiry_date < CURRENT_TIMESTAMP",
            nativeQuery = true
    )
    void deleteExpiredTokenByUser(@Param("userId") final Long userId);
}