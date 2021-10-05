package com.CS590.sample.services;

import com.CS590.sample.model.Role;
import com.CS590.sample.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }

    public Role findByName(String roleName) {
        return this.roleRepository.findRoleByRoleName(roleName);
    }


}
