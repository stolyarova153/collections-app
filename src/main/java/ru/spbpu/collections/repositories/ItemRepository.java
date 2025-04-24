package ru.spbpu.collections.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.Item;
import ru.spbpu.collections.entities.global.Global;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Transactional(readOnly = true)
    @Query("select i.global from Item i where i.id = :id")
    Optional<Global> getGlobal(@Param("id") final long id);
}