package ru.spbpu.collections.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbpu.collections.model.dto.ItemDto;
import ru.spbpu.collections.model.request.ItemSaveDto;
import ru.spbpu.collections.model.request.ItemUpdateDto;
import ru.spbpu.collections.service.ItemsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.base-prefix}" + "/items")
public class ItemsController {

    private final ItemsService itemsService;

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(itemsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ItemDto> createMine(@RequestBody final ItemSaveDto dto) {
        return ResponseEntity.ok(itemsService.create(dto));
    }

    @PatchMapping
    public ResponseEntity<ItemDto> updateMine(@RequestBody final ItemUpdateDto dto) {
        return ResponseEntity.ok(itemsService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMine(@PathVariable("id") final Long id) {

        itemsService.delete(List.of(id));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> like(@PathVariable("id") final Long id) {

        itemsService.likeItem(id, true);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<?> unlike(@PathVariable("id") final Long id) {

        itemsService.likeItem(id, false);
        return ResponseEntity.ok().build();
    }
}
