package ru.spbpu.collections.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.global.Global;

@Repository
public interface GlobalRepository extends JpaRepository<Global, Long> {

    @Modifying
    @Transactional
    @Query("update Global g set g.likes = g.likes + 1 where g.id = :id")
    void like(@Param("id") final long id);

    @Modifying
    @Transactional
    @Query("update Global g set g.likes = g.likes - 1 where g.id = :id and g.likes > 0")
    void unlike(@Param("id") final long id);
}