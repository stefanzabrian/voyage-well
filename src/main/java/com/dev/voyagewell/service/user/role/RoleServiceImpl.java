package com.dev.voyagewell.service.user.role;

import com.dev.voyagewell.controller.dto.RoleDto;
import com.dev.voyagewell.model.user.Role;
import com.dev.voyagewell.repository.user.RoleRepository;
import com.dev.voyagewell.utils.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveRole(RoleDto roleDto) {
        if (!StringUtils.hasText(roleDto.getName())) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }

        Role newRole = new Role();
        newRole.setName(roleDto.getName());

        try {
            roleRepository.save(newRole);
        } catch (DataIntegrityViolationException e) {
            logger.error("Failed to save role: Name already exists in the database", e);
            throw new DataIntegrityViolationException("Name already exists in the database");
        } catch (Exception e) {
            logger.error("Failed to save role: An unexpected error occurred", e);
            throw new RuntimeException("Failed to save role", e);
        }
    }

    @Override
    public List<RoleDto> findAll() {
        List<RoleDto> roles = new ArrayList<>();
        try {
            roleRepository.findAll().forEach(role -> {
                RoleDto roleDto = new RoleDto();
                roleDto.setId(role.getId());
                roleDto.setName(role.getName());
                roles.add(roleDto);
            });
        } catch (Exception e) {
            logger.error("Failed to fetch roles", e);
            throw new RuntimeException("Failed to fetch roles", e);
        }
        return roles;
    }

    @Override
    public RoleDto findRoleById(int id) throws ResourceNotFoundException {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with id :" + id + " don't exists"));
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto;
    }

    @Override
    public void deleteRole(RoleDto roleDto) throws ResourceNotFoundException {
        Role roleToBeDeleted = roleRepository.findById(roleDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Role with id :" + roleDto.getId() + " don't exists"));
        try {
            roleRepository.delete(roleToBeDeleted);
        } catch (DataAccessException e) {
            logger.error("Failed to delete role: An unexpected error occurred", e);
            throw new RuntimeException("Failed to delete role with id: " + roleDto.getId(), e);
        } catch (Exception e) {
            // For other unexpected exceptions
            logger.error("Failed to delete role: An unexpected error occurred", e);
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }
}
