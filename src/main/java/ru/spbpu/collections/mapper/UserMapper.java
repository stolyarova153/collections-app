package ru.spbpu.collections.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.spbpu.collections.entities.User;
import ru.spbpu.collections.model.dto.MediaDto;
import ru.spbpu.collections.model.dto.RoleDto;
import ru.spbpu.collections.model.dto.UserDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {RoleMapper.class, DateMapper.class},
        builder = @Builder(disableBuilder = true)
)
public interface UserMapper {

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "roles", source = "roles")
    UserDto toUserDto(final User user, final List<RoleDto> roles, final List<UserDto> friends, final MediaDto avatar);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "friends", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    UserDto toUserDtoSimple(final User user);
}
