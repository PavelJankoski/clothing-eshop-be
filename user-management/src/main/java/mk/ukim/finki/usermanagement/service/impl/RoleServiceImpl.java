package mk.ukim.finki.usermanagement.service.impl;

import mk.ukim.finki.usermanagement.domain.enums.RoleType;
import mk.ukim.finki.usermanagement.domain.exceptions.RoleNotFoundException;
import mk.ukim.finki.usermanagement.domain.models.Role;
import mk.ukim.finki.usermanagement.repository.RoleRepository;
import mk.ukim.finki.usermanagement.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByType(RoleType type) {
        Role role = this.roleRepository.findRoleByTypeAndIsDeletedFalse(type).orElseThrow(() -> new RoleNotFoundException(type.name()));
        return role;
    }
}
