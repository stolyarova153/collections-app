package ru.spbpu.collections.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @Transactional(readOnly = true)
    Optional<User> findById(final Long id);

    @Transactional(readOnly = true)
    @Query("select u from User u join fetch u.roles where u.email = :email")
    Optional<User> findByEmail(@Param("email") final String email);

    @Query(
            value = """
                    with f as (
                        select * from friends
                        where user1 = :userId or user2 = :userId
                    )
                    select u.*
                    from f
                    join users u on u.id in (f.user1, f.user2)
                    where f.enddate is not null and u.id <> :userId
                    """,
            nativeQuery = true
    )
    @Transactional(readOnly = true)
    List<User> findFriends(@Param("userId") final long userId);

    @Modifying
    @Query(
            value = """
                    insert into friends(user1, user2, startdate)
                    values (:userId, :friendId, CURRENT_TIMESTAMP)
                    """,
            nativeQuery = true
    )
    void addFriend(@Param("userId") final long userId, @Param("friendId") final long friendId);

    @Query(
            value = """
                    select (count(*) > 0)
                    from friends
                    where :userId in (user1, user2)
                      and :friendId in (user1, user2)
                      and enddate is not null
                    """,
            nativeQuery = true
    )
    @Transactional(readOnly = true)
    boolean areFriends(@Param("userId") final long userId, @Param("friendId") final long friendId);

    @Modifying
    @Query(
            value = """
                    update friends
                    set enddate = CURRENT_TIMESTAMP
                    where :userId in (user1, user2) and :friendId in (user1, user2)
                    """,
            nativeQuery = true
    )
    void deleteFriend(@Param("userId") final long userId, @Param("friendId") final long friendId);

}