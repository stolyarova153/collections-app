package ru.spbpu.collections.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.spbpu.collections.entities.User;
import ru.spbpu.collections.mapper.RoleMapper;
import ru.spbpu.collections.model.dto.RoleDto;
import ru.spbpu.collections.repositories.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    public List<RoleDto> findByUser(final User user) {

        if (user == null) {
            return List.of();
        }

        return roleRepository.findByUserId(user.getId())
                             .stream()
                             .map(roleMapper::toRoleDto)
                             .collect(Collectors.toList());
    }
}
