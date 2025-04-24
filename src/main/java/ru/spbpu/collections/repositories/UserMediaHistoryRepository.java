package ru.spbpu.collections.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spbpu.collections.entities.UserMediaHistory;

@Repository
public interface UserMediaHistoryRepository extends JpaRepository<UserMediaHistory, Long> {
}