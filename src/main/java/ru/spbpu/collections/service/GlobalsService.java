package ru.spbpu.collections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spbpu.collections.mapper.MediaMapper;
import ru.spbpu.collections.model.dto.MediaDto;
import ru.spbpu.collections.repositories.GlobalRepository;
import ru.spbpu.collections.repositories.MediaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GlobalsService {

    private final MediaMapper mediaMapper;
    private final MediaRepository mediaRepository;
    private final GlobalRepository globalRepository;

    public List<MediaDto> getGlobalMedias(final long globalId) {

        return mediaRepository.getGlobalMedias(globalId)
                              .stream()
                              .map(mediaMapper::toMediaDto)
                              .collect(Collectors.toList());
    }

    public void like(final Long globalId) {
        globalRepository.like(globalId);
    }

    public void unlike(final Long globalId) {
        globalRepository.unlike(globalId);
    }
}
