package com.dev.voyagewell.service.user.role;

import com.dev.voyagewell.controller.dto.role.RoleDto;
import com.dev.voyagewell.model.user.Role;
import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;

import java.util.List;

public interface RoleService {
    void saveRole(RoleDto roleDto);
    List<RoleDto> findAll();
    RoleDto findRoleById(int id) throws ResourceNotFoundException;
    void deleteRole(int id) throws ResourceNotFoundException;
    Role findRoleByName(String name) throws ResourceNotFoundException;
}
