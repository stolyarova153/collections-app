package ru.spbpu.collections.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.spbpu.collections.entities.Role;
import ru.spbpu.collections.model.dto.RoleDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-23T16:21:00+0300",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleDto toRoleDto(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setId( role.getId() );
        roleDto.setName( role.getName() );
        roleDto.setAlias( role.getAlias() );
        roleDto.setRank( role.getRank() );

        return roleDto;
    }
}
