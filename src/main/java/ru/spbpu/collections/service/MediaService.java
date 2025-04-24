package ru.spbpu.collections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.spbpu.collections.entities.Media;
import ru.spbpu.collections.entities.Mediatype;
import ru.spbpu.collections.entities.User;
import ru.spbpu.collections.mapper.MediaMapper;
import ru.spbpu.collections.model.dto.MediaDto;
import ru.spbpu.collections.model.security.UserDetailsImpl;
import ru.spbpu.collections.repositories.MediaRepository;
import ru.spbpu.collections.repositories.MediatypeRepository;

import java.io.IOException;

import static ru.spbpu.collections.model.MediaCaptionValues.AVATAR;
import static ru.spbpu.collections.utils.AuthUtils.getAuthUser;
import static ru.spbpu.collections.utils.DateTimeUtils.now;
import static ru.spbpu.collections.utils.DateTimeUtils.toTimestamp;
import static ru.spbpu.collections.utils.MediaUtils.md5;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaMapper mediaMapper;
    private final MediaRepository mediaRepository;
    private final MediatypeRepository mediatypeRepository;
    private final UserMediaHistoryService userMediaHistoryService;

    @Transactional
    public MediaDto create(final MultipartFile file, final String mediatypeName, final String caption) throws IOException {

        final Mediatype mediatype = mediatypeRepository.findByName(mediatypeName);

        return mediaMapper.toMediaDto(
                create(file, md5(now().getTime()), mediatype, file.getOriginalFilename(), caption)
        );
    }

    public Media create(final MultipartFile file, final String hash, final Mediatype mediatype, final String fileName,
                        final String caption) throws IOException {

        final Media media = mediaRepository.saveAndFlush(
                Media
                        .builder()
                        .name(fileName)
                        .size((double)file.getSize())
                        .hash(hash)
                        .caption(caption)
                        .datetime(toTimestamp(now()))
                        .file(file.getBytes())
                        .mediatype(mediatype)
                        .build()
        );

        processHistory(getAuthUser(), media);

        return media;
    }

    private void processHistory(final UserDetailsImpl user, final Media media) {

        if (user != null && media != null) {
            userMediaHistoryService.create(user.getId(), media.getId());
        }
    }

    public Media findById(final Long mediaId) {
        return mediaRepository.findById(mediaId).orElseThrow();
    }

    public MediaDto getAvatar(final User user) {

        if (user == null) {
            return null;
        }

        return mediaMapper.toMediaDto(mediaRepository.findUserMediaByCaption(user.getId(), AVATAR));
    }

    public Media findByHash(final String hash) {
        return mediaRepository.findByHash(hash).orElse(null);
    }
}
