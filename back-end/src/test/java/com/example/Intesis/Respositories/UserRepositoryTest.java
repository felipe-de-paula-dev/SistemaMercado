package com.example.Intesis.Respositories;

import com.example.Intesis.Entity.User.RegistroDto;
import com.example.Intesis.Entity.User.User;
import com.example.Intesis.Entity.User.UserRole;
import jakarta.persistence.EntityManager;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Retorno de usuario com sucesso")
    void findByLoginCase1() {
        RegistroDto newRegistro = new RegistroDto("login", "1234", UserRole.ADMINISTRADOR);
        this.criarUsuario(newRegistro);
        UserDetails UsuarioEncontrado = this.userRepository.findByLogin("login");
        assertThat(UsuarioEncontrado).isNotNull();
        assertThat(UsuarioEncontrado.getUsername()).isEqualTo("login");
    }

    @Test
    @DisplayName("Retorno de usuario sem sucesso")
    void findByLoginCase2() {
        UserDetails UsuarioEncontrado = this.userRepository.findByLogin("login");
        assertThat(UsuarioEncontrado).isNull();
    }

    @Test
    void findByLoginContainingIgnoreCase1() {
        RegistroDto newRegistro = new RegistroDto("login", "1234", UserRole.ADMINISTRADOR);
        this.criarUsuario(newRegistro);
        List<User> UsuarioEncontrado = this.userRepository.findByLoginContainingIgnoreCase("login");
        assertThat(UsuarioEncontrado).isNotEmpty();
    }

    @Test
    void findByLoginContainingIgnoreCase2() {
        List<User> UsuarioEncontrado = this.userRepository.findByLoginContainingIgnoreCase("login");
        assertThat(UsuarioEncontrado).isEmpty();
    }

    private User criarUsuario(RegistroDto data) {
        User newUser = new User(data);
        this.userRepository.save(newUser);
        return newUser;
    }
}