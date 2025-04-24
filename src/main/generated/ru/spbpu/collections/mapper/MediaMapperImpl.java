package ru.spbpu.collections.mapper;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.spbpu.collections.entities.Media;
import ru.spbpu.collections.model.dto.MediaDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-23T16:21:00+0300",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class MediaMapperImpl extends MediaMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public MediaDto toMediaDto(Media media) {
        if ( media == null ) {
            return null;
        }

        MediaDto mediaDto = new MediaDto();

        mediaDto.setId( media.getId() );
        mediaDto.setName( media.getName() );
        mediaDto.setSize( media.getSize() );
        mediaDto.setHash( media.getHash() );
        mediaDto.setCaption( media.getCaption() );
        mediaDto.setDatetime( dateMapper.timestampToDateFormat( media.getDatetime() ) );
        mediaDto.setMediatype( media.getMediatype() );

        setLink( mediaDto );

        return mediaDto;
    }
}
