package ru.spbpu.collections.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaveDto {

    private String name;
    private String origin;
    private Short creationYear;
    private Long handDatetime;
    private String materials;
    private String description;
}
