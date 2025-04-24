package ru.spbpu.collections.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(
            value = """
                    select * from roles r
                    join user2role u2r on r.id = u2r.role
                    where u2r."user" = :userId
                    """,
            nativeQuery = true
    )
    @Transactional(readOnly = true)
    List<Role> findByUserId(@Param("userId") final long userId);

}