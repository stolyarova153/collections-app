package ru.spbpu.collections.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link ru.spbpu.collections.entities.Collection}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CollectionDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String lastModified;
    private Long userId;
    private List<ItemDto> items = new ArrayList<>();
    private Long globalId;
    private String datetime;
    private Long likes;

    private List<MediaDto> medias = new ArrayList<>();
}