package ru.spbpu.collections.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.spbpu.collections.entities.Role;
import ru.spbpu.collections.model.dto.RoleDto;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        builder = @Builder(disableBuilder = true)
)
public interface RoleMapper {

    RoleDto toRoleDto(final Role role);
}
