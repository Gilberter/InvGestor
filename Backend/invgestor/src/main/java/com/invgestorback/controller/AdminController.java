package com.invgestorback.controller;

import com.invgestorback.model.User;
import com.invgestorback.repository.RoleRepository;
import com.invgestorback.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.invgestorback.service.UserService;
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    public final UserService userService;
    public final RoleRepository roleRepository;
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }
    @GetMapping("/remove-user")
    public String removeUser() {
        return  "UserRemoved";
    }

    @PostMapping("/assign-role")
    public User assignRole(@RequestBody AdminController.RoleRequest request) {
        return userService.assignRoleToUser(request.getEmail(), request.getRolename());
    }

    @PostMapping("/remove-rol")
    public User removeRole(@RequestBody AdminController.RoleRequest request) {
        return userService.deleteRoleFromUser(request.getEmail(), request.getRolename());
    }

    public static class RoleRequest {
        private String email;
        private String rolename;
        public String getEmail() { return email; }

        public String getRolename() { return rolename; }


    }
}
