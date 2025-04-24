package ru.spbpu.collections.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.spbpu.collections.config.ApplicationConfig;
import ru.spbpu.collections.config.ApplicationContextProvider;
import ru.spbpu.collections.entities.Media;
import ru.spbpu.collections.model.dto.MediaDto;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = DateMapper.class,
        builder = @Builder(disableBuilder = true)
)
public abstract class MediaMapper {

    @Autowired
    protected ApplicationConfig applicationConfig;

    @Mapping(target = "link", ignore = true)
    public abstract MediaDto toMediaDto(final Media media);

    @AfterMapping
    protected void setLink(@MappingTarget final MediaDto mediaDto) {

        final String link = applicationConfig.getBasePrefix()
                                             .concat("/medias/")
                                             .concat(mediaDto.getHash());

        mediaDto.setLink(link);
    }
}
