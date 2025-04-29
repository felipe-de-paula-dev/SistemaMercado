package com.example.Intesis.Controllers.User;

import com.example.Intesis.Entity.Cliente.Cliente;
import com.example.Intesis.Entity.User.User;
import com.example.Intesis.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/search")
    private List<User> clienteName(@RequestParam String name){ return userRepository.findByLoginContainingIgnoreCase(name); }

    @GetMapping
    private ResponseEntity<List<User>> getUser(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> delete(@PathVariable UUID id){
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok("Usuario Apagado");
        } catch (Exception e) {
            return ResponseEntity.ok("Usuario Não Foi Apagado " + e);
        }
    }

}
