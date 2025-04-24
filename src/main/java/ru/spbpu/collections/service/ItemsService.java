package ru.spbpu.collections.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.global.Global;
import ru.spbpu.collections.mapper.ItemMapper;
import ru.spbpu.collections.model.dto.ItemDto;
import ru.spbpu.collections.model.request.ItemSaveDto;
import ru.spbpu.collections.model.request.ItemUpdateDto;
import ru.spbpu.collections.model.security.UserDetailsImpl;
import ru.spbpu.collections.repositories.Item2CollectionRepository;
import ru.spbpu.collections.repositories.ItemRepository;

import java.util.List;

import static java.util.Optional.ofNullable;
import static ru.spbpu.collections.utils.AuthUtils.getAuthUser;

@Service
@RequiredArgsConstructor
public class ItemsService implements Likeable {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final GlobalsService globalsService;
    private final Item2CollectionRepository item2CollectionRepository;

    @Transactional(readOnly = true)
    public ItemDto findById(final Long id) {
        return itemMapper.toItemDto(itemRepository.findById(id).orElse(null));
    }

    @Transactional
    public ItemDto create(final ItemSaveDto dto) {
        return createOrUpdate(dto, getAuthUser());
    }

    @Transactional
    public ItemDto update(final ItemUpdateDto dto) {
        return createOrUpdate(dto, getAuthUser());
    }

    private <DTO extends ItemSaveDto> ItemDto createOrUpdate(final DTO dto, final UserDetailsImpl user) {

        return ofNullable(user)
                .map(u -> itemMapper.toItemDto(itemRepository.save(itemMapper.toItem(dto))))
                .orElseThrow(() -> new IllegalStateException("User is null"));
    }

    @Transactional
    public void delete(final List<Long> ids) {

        itemRepository.deleteAllById(ids);
        deleteRelations(ids);
    }

    public void deleteRelations(final List<Long> itemIds) {
        item2CollectionRepository.deleteRelations(itemIds);
    }

    public void likeItem(final Long id, final boolean increment) {
        likeGlobal(getItemGlobal(id).getId(), increment);
    }

    @Override
    public void likeGlobal(final Long globalId, final boolean increment) {

        if (increment) {
            globalsService.like(globalId);
        } else {
            globalsService.unlike(globalId);
        }
    }

    private Global getItemGlobal(final Long id) {
        return itemRepository.getGlobal(id).orElseThrow(EntityNotFoundException::new);
    }
}
