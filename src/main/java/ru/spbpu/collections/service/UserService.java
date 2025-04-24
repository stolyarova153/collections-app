package ru.spbpu.collections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbpu.collections.entities.User;
import ru.spbpu.collections.mapper.UserMapper;
import ru.spbpu.collections.model.dto.UserDto;
import ru.spbpu.collections.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.spbpu.collections.utils.AuthUtils.getAuthUser;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final MediaService mediaService;

    public UserDto findById(final long id) {
        return userMapper.toUserDtoSimple(userRepository.findById(id).orElse(null));
    }

    public UserDto getUser() {

        final Optional<User> user = userRepository.findById(getAuthUser().getId());

        return user.map(u -> userMapper.toUserDto(
                           u, roleService.findByUser(u), getFriends(u), mediaService.getAvatar(u)
                   ))
                   .orElse(null);
    }

    public List<UserDto> getFriends(final User user) {

        if (user == null) {
            return List.of();
        }

        return userRepository.findFriends(user.getId())
                             .stream()
                             .map(userMapper::toUserDtoSimple)
                             .collect(Collectors.toList());
    }

    @Transactional
    public UserDto addFriend(final Long friendId) {

        final Long userId = getAuthUser().getId();

        checkFriends(userId, friendId);

        userRepository.addFriend(userId, friendId);

        return getUser();
    }

    @Transactional
    public UserDto deleteFriend(final Long friendId) {

        userRepository.deleteFriend(getAuthUser().getId(), friendId);

        return getUser();
    }

    private void checkFriends(final long userId, final long friendId) {

        if (userId == 0L) {
            throw new IllegalStateException("Current user not found");
        }

        if (userRepository.areFriends(userId, friendId)) {
            throw new IllegalStateException("You are already friends");
        }
    }
}
