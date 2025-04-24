package ru.spbpu.collections.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.spbpu.collections.entities.Collection;
import ru.spbpu.collections.entities.Item;
import ru.spbpu.collections.entities.global.Global;
import ru.spbpu.collections.model.dto.CollectionDto;
import ru.spbpu.collections.model.dto.ItemDto;
import ru.spbpu.collections.model.request.CollectionSaveDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-23T16:21:00+0300",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class CollectionMapperImpl implements CollectionMapper {

    @Autowired
    private DateMapper dateMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public CollectionDto toCollectionDto(Collection collection) {
        if ( collection == null ) {
            return null;
        }

        CollectionDto collectionDto = new CollectionDto();

        collectionDto.setId( collection.getId() );
        collectionDto.setName( collection.getName() );
        collectionDto.setDescription( collection.getDescription() );
        Long likes = collectionGlobalLikes( collection );
        if ( likes != null ) {
            collectionDto.setLikes( likes );
        }
        else {
            collectionDto.setLikes( (long) 0L );
        }
        collectionDto.setGlobalId( collectionGlobalId( collection ) );
        collectionDto.setDatetime( dateMapper.timestampToDateFormat( collectionGlobalDatetime( collection ) ) );
        collectionDto.setLastModified( dateMapper.timestampToDateFormat( collection.getLastModified() ) );
        collectionDto.setUserId( collection.getUserId() );
        collectionDto.setItems( itemListToItemDtoList( collection.getItems() ) );

        return collectionDto;
    }

    @Override
    public Collection toCollection(CollectionSaveDto dto, Long userId) {
        if ( dto == null && userId == null ) {
            return null;
        }

        Collection collection = new Collection();

        if ( dto != null ) {
            collection.setName( dto.getName() );
            collection.setDescription( dto.getDescription() );
        }
        collection.setUserId( userId );

        setGlobal( collection, dto );

        return collection;
    }

    private Long collectionGlobalLikes(Collection collection) {
        if ( collection == null ) {
            return null;
        }
        Global global = collection.getGlobal();
        if ( global == null ) {
            return null;
        }
        Long likes = global.getLikes();
        if ( likes == null ) {
            return null;
        }
        return likes;
    }

    private Long collectionGlobalId(Collection collection) {
        if ( collection == null ) {
            return null;
        }
        Global global = collection.getGlobal();
        if ( global == null ) {
            return null;
        }
        Long id = global.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Timestamp collectionGlobalDatetime(Collection collection) {
        if ( collection == null ) {
            return null;
        }
        Global global = collection.getGlobal();
        if ( global == null ) {
            return null;
        }
        Timestamp datetime = global.getDatetime();
        if ( datetime == null ) {
            return null;
        }
        return datetime;
    }

    protected List<ItemDto> itemListToItemDtoList(List<Item> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemDto> list1 = new ArrayList<ItemDto>( list.size() );
        for ( Item item : list ) {
            list1.add( itemMapper.toItemDto( item ) );
        }

        return list1;
    }
}
