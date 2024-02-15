package com.dev.voyagewell.service.user.role;

import com.dev.voyagewell.controller.dto.RoleDto;
import com.dev.voyagewell.utils.exception.ResourceNotFoundException;

import java.util.List;

public interface RoleService {
    void saveRole(RoleDto roleDto);
    List<RoleDto> findAll();
    RoleDto findRoleById(int id) throws ResourceNotFoundException;
    void deleteRole(RoleDto roleDto) throws ResourceNotFoundException;
}
