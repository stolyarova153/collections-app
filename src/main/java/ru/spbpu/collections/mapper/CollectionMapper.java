package ru.spbpu.collections.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.spbpu.collections.entities.Collection;
import ru.spbpu.collections.entities.global.Global;
import ru.spbpu.collections.model.dto.CollectionDto;
import ru.spbpu.collections.model.request.CollectionSaveDto;
import ru.spbpu.collections.model.request.CollectionUpdateDto;

import java.sql.Timestamp;

import static ru.spbpu.collections.utils.DateTimeUtils.now;
import static ru.spbpu.collections.utils.DateTimeUtils.toTimestamp;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {DateMapper.class, ItemMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface CollectionMapper {

    @Mapping(target = "medias", ignore = true)
    @Mapping(target = "id", source = "collection.id")
    @Mapping(target = "name", source = "collection.name")
    @Mapping(target = "description", source = "collection.description")
    @Mapping(target = "likes", source = "collection.global.likes", defaultValue = "0L")
    @Mapping(target = "globalId", source = "collection.global.id")
    @Mapping(target = "datetime", source = "collection.global.datetime")
    CollectionDto toCollectionDto(final Collection collection);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "global", ignore = true)
    @Mapping(target = "itemsCount", ignore = true)
    @Mapping(target = "lastModified", ignore = true)
    @Mapping(target = "name", source = "dto.name")
    Collection toCollection(final CollectionSaveDto dto, final Long userId);

    @AfterMapping
    default void setGlobal(@MappingTarget final Collection collection, final CollectionSaveDto dto) {

        final Timestamp timestamp = toTimestamp(now());

        collection.setGlobal(new Global(timestamp));

        if (dto instanceof CollectionUpdateDto updateDto) {

            collection.setId(updateDto.getId());
            collection.setLastModified(timestamp);
        }
    }
}
