package com.ne.template.services;

import com.ne.template.enums.ERole;
import com.ne.template.models.Role;

import java.util.List;
import java.util.UUID;

public interface IRoleService {
    public List<Role> getAllRoles();
    public Role getRoleById(UUID id);
    public Role getRoleByName(ERole roleName);
    public Role registerRole(ERole roleName);
    public boolean isRolePresent(ERole roleName);
    public Role deleteRoleById(UUID id);


}
