package com.dev.voyagewell.controller.admin.role;

import com.dev.voyagewell.controller.dto.role.RoleDto;
import com.dev.voyagewell.service.user.role.RoleService;
import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/role")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRole(@RequestBody @Valid RoleDto roleDto) {
        try {
            roleService.saveRole(roleDto);
            return ResponseEntity.status(HttpStatus.OK).body("Role created");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        try {
            List<RoleDto> roles = roleService.findAll();
            if (roles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<RoleDto>());
            }
            return ResponseEntity.status(HttpStatus.OK).body(roles);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") int id) {
        try {
            RoleDto roleDto = roleService.findRoleById(id);
            return ResponseEntity.status(HttpStatus.OK).body(roleDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") int id) {
        try {
            roleService.deleteRole(id);
            return ResponseEntity.status(HttpStatus.OK).body("Role with id: " + id + " deleted!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
