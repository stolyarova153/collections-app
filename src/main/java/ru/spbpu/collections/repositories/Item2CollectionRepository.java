package ru.spbpu.collections.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.Item2Collection;
import ru.spbpu.collections.entities.Item2CollectionId;

import java.util.List;

@Repository
public interface Item2CollectionRepository extends JpaRepository<Item2Collection, Item2CollectionId> {

    @Modifying
    @Transactional
    @Query("delete from Item2Collection ic where ic.id.item in (:itemIds)")
    void deleteRelations(@Param("itemIds") final List<Long> itemIds);
}