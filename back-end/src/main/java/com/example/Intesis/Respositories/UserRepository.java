package com.example.Intesis.Respositories;

import com.example.Intesis.Entity.Cliente.Cliente;
import com.example.Intesis.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByLogin(String login);
    List<User> findByLoginContainingIgnoreCase(String login);

}
