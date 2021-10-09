package mk.ukim.finki.usermanagement.service;

import mk.ukim.finki.usermanagement.domain.enums.RoleType;
import mk.ukim.finki.usermanagement.domain.models.Role;

public interface RoleService {
    Role findRoleByType(RoleType type);
}
