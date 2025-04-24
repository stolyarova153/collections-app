package ru.spbpu.collections.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.Media;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    @Override
    @Transactional(readOnly = true)
    Optional<Media> findById(final Long id);

    @Transactional(readOnly = true)
    Optional<Media> findByHash(final String hash);

    @Override
    @Transactional
    <S extends Media> S saveAndFlush(final S entity);

    @Query(
            value = """
                    select m.*
                    from usermediahistory um
                    join medias m on um.media = m.id and m.caption = :caption
                    where um.user = :userId
                    order by um.datetime desc
                    limit 1
                    """,
            nativeQuery = true
    )
    @Transactional(readOnly = true)
    Media findUserMediaByCaption(@Param("userId") final long userId, @Param("caption") final String caption);

    @Query(
            value = """
                    select m.*
                    from globalmedias gm
                    join globals g on g.id = gm.global and g.id = :globalId
                    join medias m on m.id = gm.media
                    """,
            nativeQuery = true
    )
    @Transactional(readOnly = true)
    List<Media> getGlobalMedias(@Param("globalId") final long globalId);

}
