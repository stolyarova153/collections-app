package ru.spbpu.collections.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionUpdateDto extends CollectionSaveDto {

    private long id;
}
