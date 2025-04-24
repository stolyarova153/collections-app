package ru.spbpu.collections.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.spbpu.collections.entities.Media;
import ru.spbpu.collections.model.dto.MediaDto;
import ru.spbpu.collections.service.MediaService;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;
import static ru.spbpu.collections.utils.MediaUtils.ATTACHMENT_FILENAME_PATTERN;
import static ru.spbpu.collections.utils.MediaUtils.encodeUTF8;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.base-prefix}" + "/medias")
public class MediaController {

    private final MediaService mediaService;

    @GetMapping("/{hash}")
    public ResponseEntity<Resource> getMedia(@PathVariable("hash") final String hash) {

        final Media media = mediaService.findByHash(hash);

        if (media == null) {
            return ResponseEntity.notFound().build();
        }

        final Resource resource = new ByteArrayResource(media.getFile()) {
            @Override
            public String getFilename() {
                return media.getName();
            }
        };

        return ResponseEntity.ok()
                             .contentType(APPLICATION_OCTET_STREAM)
                             .header(
                                     CONTENT_DISPOSITION, String.format(
                                             ATTACHMENT_FILENAME_PATTERN, encodeUTF8(media.getName())
                                     )
                             )
                             .body(resource);
    }

    @PostMapping("/{mediaType}/for/{caption}")
    public ResponseEntity<MediaDto> createMedia(@RequestParam("file") final MultipartFile file,
                                                @PathVariable("mediaType") final String mediaType,
                                                @PathVariable("caption") String caption) {

        try {
            final MediaDto media = mediaService.create(file, mediaType, caption);
            return ResponseEntity.ok(media);
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
