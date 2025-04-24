package ru.spbpu.collections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.UserMediaHistory;
import ru.spbpu.collections.repositories.UserMediaHistoryRepository;

import static ru.spbpu.collections.utils.DateTimeUtils.now;
import static ru.spbpu.collections.utils.DateTimeUtils.toTimestamp;

@Service
@RequiredArgsConstructor
public class UserMediaHistoryService {

    private final UserMediaHistoryRepository userMediaHistoryRepository;

    @Transactional
    public void create(final Long userId, final Long mediaId) {

        userMediaHistoryRepository.save(
                UserMediaHistory
                        .builder()
                        .userId(userId)
                        .mediaId(mediaId)
                        .datetime(toTimestamp(now()))
                        .build()
        );
    }

}
