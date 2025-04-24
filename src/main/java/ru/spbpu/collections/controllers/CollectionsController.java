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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spbpu.collections.model.dto.CollectionDto;
import ru.spbpu.collections.model.dto.ItemDto;
import ru.spbpu.collections.model.request.CollectionSaveDto;
import ru.spbpu.collections.model.request.CollectionUpdateDto;
import ru.spbpu.collections.model.request.ItemSaveDto;
import ru.spbpu.collections.service.CollectionsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.base-prefix}" + "/collections")
public class CollectionsController {

    private final CollectionsService collectionsService;

    @GetMapping("/by-user/{id}")
    public ResponseEntity<List<CollectionDto>> getUserCollections(@PathVariable("id") final Long id,
                                                                  @RequestParam(
                                                                          value = "filter", required = false,
                                                                          defaultValue = "alphabet"
                                                                  ) final String filter) {
        return ResponseEntity.ok(collectionsService.findByUserSimple(id, filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionDto> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(collectionsService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CollectionDto> createMine(@RequestBody final CollectionSaveDto dto) {
        return ResponseEntity.ok(collectionsService.create(dto));
    }

    @PatchMapping
    public ResponseEntity<CollectionDto> updateMine(@RequestBody final CollectionUpdateDto dto) {
        return ResponseEntity.ok(collectionsService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMine(@PathVariable("id") final Long id) {

        collectionsService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<List<ItemDto>> addItems(@PathVariable("id") final Long id,
                                                  @RequestBody final List<ItemSaveDto> items) {
        return ResponseEntity.ok(collectionsService.addItems(id, items));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> like(@PathVariable("id") final Long id) {

        collectionsService.likeCollection(id, true);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<?> unlike(@PathVariable("id") final Long id) {

        collectionsService.likeCollection(id, false);
        return ResponseEntity.ok().build();
    }
}
