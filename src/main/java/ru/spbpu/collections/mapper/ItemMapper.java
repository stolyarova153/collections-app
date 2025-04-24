package ru.spbpu.collections.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.spbpu.collections.entities.Item;
import ru.spbpu.collections.entities.global.Global;
import ru.spbpu.collections.model.dto.ItemDto;
import ru.spbpu.collections.model.request.ItemSaveDto;

import static ru.spbpu.collections.utils.DateTimeUtils.now;
import static ru.spbpu.collections.utils.DateTimeUtils.toTimestamp;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = DateMapper.class,
        builder = @Builder(disableBuilder = true)
)
public interface ItemMapper {

    @Mapping(target = "medias", ignore = true)
    @Mapping(target = "globalId", source = "item.global.id")
    @Mapping(target = "datetime", source = "item.global.datetime")
    ItemDto toItemDto(final Item item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "global", ignore = true)
    Item toItem(final ItemSaveDto dto);

    @AfterMapping
    default void setGlobal(@MappingTarget final Item item) {
        item.setGlobal(new Global(toTimestamp(now())));
    }
}
