package com.ne.template.serviceImpls;

import com.ne.template.enums.ERole;
import com.ne.template.exceptions.BadRequestAlertException;
import com.ne.template.exceptions.NotFoundException;
import com.ne.template.models.Role;
import com.ne.template.repositories.IRoleRepository;
import com.ne.template.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleServiceImpl implements IRoleService {
    private IRoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(UUID id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role with id " + id + "is not found"));
        return role;
    }

    @Override
    public Role getRoleByName(ERole roleName) {
        Role role = roleRepository.findByRoleName(roleName).get();
        if (role == null) {
            throw new NotFoundException("Role with name " + roleName + " is not found");
        }
        return role;
    }

    @Override
    public Role registerRole(ERole roleName) {
        Optional<Role> isRoleExistent = roleRepository.findByRoleName(roleName);
        if(isRoleExistent.isPresent()) {
            throw new BadRequestAlertException(String.format("Role with name %s already exists", roleName));
        }
        Role newRole = new Role(roleName);
        return roleRepository.save(newRole);

    }

    @Override
    public boolean isRolePresent(ERole roleName) {
        return roleRepository.findByRoleName(roleName).isPresent();
    }

    @Override
    public Role deleteRoleById(UUID id) {
        Role roleToRemove = roleRepository.findById(id).orElseThrow(()->new NotFoundException("Role with id " + id + "is not found"));
        roleRepository.delete(roleToRemove);
        return roleToRemove;
    }
}
