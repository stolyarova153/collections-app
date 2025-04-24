package ru.spbpu.collections.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.Collection;
import ru.spbpu.collections.entities.global.Global;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Transactional(readOnly = true)
    List<Collection> findByUserId(final Long userId, final Sort sort);

    @Override
    @Transactional(readOnly = true)
    Optional<Collection> findById(final Long id);

    @Transactional(readOnly = true)
    @Query("select c from Collection c left join fetch c.items where c.id = :id")
    Optional<Collection> findByIdWithItems(@Param("id") final Long id);

    @Query(
            value = """
                    select i.id
                    from items i
                    join items2collections ic on i.id = ic.item and ic.collection = :id
                    """,
            nativeQuery = true
    )
    @Transactional(readOnly = true)
    List<Long> findItemIds(@Param("id") final Long id);

    @Transactional(readOnly = true)
    @Query("select c.global from Collection c where c.id = :id")
    Optional<Global> getGlobal(@Param("id") final long id);

}
