package ru.spbpu.collections.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link ru.spbpu.collections.entities.Item}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ItemDto implements Serializable {

    private Long id;
    private String name;
    private String origin;
    private Short creationYear;
    private String handDatetime;
    private String materials;
    private String description;
    private Long globalId;
    private String datetime;

    private List<MediaDto> medias = new ArrayList<>();
}