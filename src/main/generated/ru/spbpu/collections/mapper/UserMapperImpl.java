package ru.spbpu.collections.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.spbpu.collections.entities.User;
import ru.spbpu.collections.model.dto.MediaDto;
import ru.spbpu.collections.model.dto.RoleDto;
import ru.spbpu.collections.model.dto.UserDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-23T16:21:00+0300",
    comments = "version: 1.5.1.Final, compiler: javac, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public UserDto toUserDto(User user, List<RoleDto> roles, List<UserDto> friends, MediaDto avatar) {
        if ( user == null && roles == null && friends == null && avatar == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        if ( user != null ) {
            userDto.setId( user.getId() );
            userDto.setName( user.getName() );
            userDto.setLastname( user.getLastname() );
            userDto.setPatronymic( user.getPatronymic() );
            userDto.setBirthdate( dateMapper.timestampToDateFormat( user.getBirthdate() ) );
            userDto.setEmail( user.getEmail() );
            userDto.setPhone( user.getPhone() );
        }
        List<RoleDto> list = roles;
        if ( list != null ) {
            userDto.setRoles( new ArrayList<RoleDto>( list ) );
        }
        List<UserDto> list1 = friends;
        if ( list1 != null ) {
            userDto.setFriends( new ArrayList<UserDto>( list1 ) );
        }
        userDto.setAvatar( avatar );

        return userDto;
    }

    @Override
    public UserDto toUserDtoSimple(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setLastname( user.getLastname() );
        userDto.setName( user.getName() );
        userDto.setPatronymic( user.getPatronymic() );
        userDto.setBirthdate( dateMapper.timestampToDateFormat( user.getBirthdate() ) );
        userDto.setEmail( user.getEmail() );
        userDto.setPhone( user.getPhone() );

        return userDto;
    }
}
