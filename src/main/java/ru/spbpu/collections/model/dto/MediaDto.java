package ru.spbpu.collections.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.spbpu.collections.entities.Mediatype;

import java.io.Serializable;

/**
 * DTO for {@link ru.spbpu.collections.entities.Media}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MediaDto implements Serializable {

    private Long id;
    private String name;
    private Double size;
    private String hash;
    private String caption;
    private String datetime;
    private String link;
    private Mediatype mediatype;
}