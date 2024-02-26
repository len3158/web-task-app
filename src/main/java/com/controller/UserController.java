package com.controller;

import com.model.UserDTO;
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
    public ResponseEntity<UserDTO> subscribeUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.subscribeUser(userDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserInfo(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.getUserById(id, userDTO));
    }

}
