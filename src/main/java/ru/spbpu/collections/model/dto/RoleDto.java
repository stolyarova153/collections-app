package ru.spbpu.collections.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * DTO for {@link ru.spbpu.collections.entities.Role}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RoleDto implements Serializable {

    private Long id;
    private String name;
    private String alias;
    private Integer rank;
}