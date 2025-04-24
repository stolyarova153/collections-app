package ru.spbpu.collections.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link ru.spbpu.collections.entities.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class UserDto implements Serializable {

    private Long id;
    private String lastname;
    private String name;
    private String patronymic;
    private String birthdate;
    private String email;
    private String phone;

    private List<RoleDto> roles;
    private List<UserDto> friends;
    private MediaDto avatar;
}