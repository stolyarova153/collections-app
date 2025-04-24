package ru.spbpu.collections.mapper;

import java.sql.Timestamp;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.spbpu.collections.entities.Item;
import ru.spbpu.collections.entities.global.Global;
import ru.spbpu.collections.model.dto.ItemDto;
import ru.spbpu.collections.model.request.ItemSaveDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-23T16:21:00+0300",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class ItemMapperImpl implements ItemMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public ItemDto toItemDto(Item item) {
        if ( item == null ) {
            return null;
        }

        ItemDto itemDto = new ItemDto();

        itemDto.setGlobalId( itemGlobalId( item ) );
        itemDto.setDatetime( dateMapper.timestampToDateFormat( itemGlobalDatetime( item ) ) );
        itemDto.setId( item.getId() );
        itemDto.setName( item.getName() );
        itemDto.setOrigin( item.getOrigin() );
        itemDto.setCreationYear( item.getCreationYear() );
        itemDto.setHandDatetime( dateMapper.timestampToDateFormat( item.getHandDatetime() ) );
        itemDto.setMaterials( item.getMaterials() );
        itemDto.setDescription( item.getDescription() );

        return itemDto;
    }

    @Override
    public Item toItem(ItemSaveDto dto) {
        if ( dto == null ) {
            return null;
        }

        Item item = new Item();

        item.setName( dto.getName() );
        item.setOrigin( dto.getOrigin() );
        item.setCreationYear( dto.getCreationYear() );
        item.setHandDatetime( dateMapper.toTimestamp( dto.getHandDatetime() ) );
        item.setMaterials( dto.getMaterials() );
        item.setDescription( dto.getDescription() );

        setGlobal( item );

        return item;
    }

    private Long itemGlobalId(Item item) {
        if ( item == null ) {
            return null;
        }
        Global global = item.getGlobal();
        if ( global == null ) {
            return null;
        }
        Long id = global.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Timestamp itemGlobalDatetime(Item item) {
        if ( item == null ) {
            return null;
        }
        Global global = item.getGlobal();
        if ( global == null ) {
            return null;
        }
        Timestamp datetime = global.getDatetime();
        if ( datetime == null ) {
            return null;
        }
        return datetime;
    }
}
