package ru.spbpu.collections.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.Item;
import ru.spbpu.collections.entities.Item2Collection;
import ru.spbpu.collections.entities.Item2CollectionId;
import ru.spbpu.collections.entities.global.Global;
import ru.spbpu.collections.mapper.CollectionMapper;
import ru.spbpu.collections.mapper.ItemMapper;
import ru.spbpu.collections.model.dto.CollectionDto;
import ru.spbpu.collections.model.dto.ItemDto;
import ru.spbpu.collections.model.request.CollectionSaveDto;
import ru.spbpu.collections.model.request.CollectionUpdateDto;
import ru.spbpu.collections.model.request.ItemSaveDto;
import ru.spbpu.collections.model.security.UserDetailsImpl;
import ru.spbpu.collections.repositories.CollectionRepository;
import ru.spbpu.collections.repositories.Item2CollectionRepository;
import ru.spbpu.collections.repositories.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static ru.spbpu.collections.model.CollectionFilterValues.getProperty;
import static ru.spbpu.collections.utils.AuthUtils.getAuthUser;

@Service
@RequiredArgsConstructor
public class CollectionsService implements Likeable {

    private final ItemMapper itemMapper;
    private final GlobalsService globalsService;
    private final CollectionMapper collectionMapper;
    private final CollectionRepository collectionRepository;
    private final ItemRepository itemRepository;
    private final Item2CollectionRepository item2CollectionRepository;
    private final ItemsService itemsService;

    public List<CollectionDto> findByUserSimple(final Long user, final String filter) {

        return collectionRepository.findByUserId(user, Sort.by(getProperty(filter)))
                                   .stream()
                                   .map(collectionMapper::toCollectionDto)
                                   .peek(c -> c.setMedias(globalsService.getGlobalMedias(c.getGlobalId())))
                                   .collect(Collectors.toList());
    }

    public CollectionDto findById(final long id) {

        final CollectionDto dto
                = collectionMapper.toCollectionDto(collectionRepository.findByIdWithItems(id).orElse(null));

        processMedias(dto);

        return dto;
    }

    @Transactional
    public CollectionDto create(final CollectionSaveDto dto) {
        return createOrUpdate(dto, getAuthUser());
    }

    @Transactional
    public CollectionDto update(final CollectionUpdateDto dto) {
        return createOrUpdate(dto, getAuthUser());
    }

    private <DTO extends CollectionSaveDto> CollectionDto createOrUpdate(final DTO dto, final UserDetailsImpl user) {

        return ofNullable(user)
                .map(u -> collectionMapper.toCollectionDto(
                        collectionRepository.save(collectionMapper.toCollection(dto, u.getId()))
                ))
                .orElseThrow(() -> new IllegalStateException("User is null"));
    }

    @Transactional
    public void delete(final Long id) {

        itemsService.delete(collectionRepository.findItemIds(id));
        collectionRepository.deleteById(id);
    }

    @Transactional
    public List<ItemDto> addItems(final Long collectionId, final List<ItemSaveDto> items) {

        final List<Item> savedItems = itemRepository.saveAllAndFlush(
                items.stream().map(itemMapper::toItem).collect(Collectors.toList())
        );

        item2CollectionRepository.saveAll(
                savedItems
                        .stream()
                        .map(i -> new Item2Collection(new Item2CollectionId(collectionId, i.getId())))
                        .collect(Collectors.toList())
        );

        return savedItems
                .stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public void likeCollection(final Long id, final boolean increment) {
        likeGlobal(getCollectionGlobal(id).getId(), increment);
    }

    @Override
    public void likeGlobal(final Long globalId, final boolean increment) {

        if (increment) {
            globalsService.like(globalId);
        } else {
            globalsService.unlike(globalId);
        }
    }

    private Global getCollectionGlobal(final Long id) {
        return collectionRepository.getGlobal(id).orElseThrow(EntityNotFoundException::new);
    }

    private void processMedias(final CollectionDto dto) {

        ofNullable(dto).ifPresent(
                c -> {
                    c.getItems().forEach(i -> i.setMedias(globalsService.getGlobalMedias(i.getGlobalId())));
                    c.setMedias(globalsService.getGlobalMedias(c.getGlobalId()));
                }
        );
    }
}
