package ru.spbpu.collections.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.Mediatype;

@Repository
public interface MediatypeRepository extends JpaRepository<Mediatype, Long> {

    @Transactional(readOnly = true)
    Mediatype findByName(final String name);
}