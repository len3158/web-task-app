package com.controller;

import com.datamodel.UserDTO;
import com.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/subscribe")
    public ResponseEntity<UtilisateurDTO> subscribeUser(@RequestBody UtilisateurDTO utilisateurDTO) {
        return ResponseEntity.ok(userService.subscribeUser(utilisateurDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // TODO: Consider user profile update
}
