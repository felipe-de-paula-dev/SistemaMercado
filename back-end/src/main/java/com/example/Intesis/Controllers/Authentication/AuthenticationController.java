package com.example.Intesis.Controllers.Authentication;


import com.example.Intesis.Entity.User.*;
import com.example.Intesis.Respositories.UserRepository;
import com.example.Intesis.Services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDto Dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(Dto.login(), Dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.tokenGerado((User) auth.getPrincipal());
        var user = (User) auth.getPrincipal();


        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new LoginDTO(token, roles));
    }


    @PostMapping("/registrar")
    public ResponseEntity<String> registro(@RequestBody RegistroDto Dto){
        try{
            if(this.userRepository.findByLogin(Dto.login()) != null){
                return ResponseEntity.badRequest().build();
            }

            String senhaCripto = new BCryptPasswordEncoder().encode(Dto.password());

            User newUser = new User(Dto.login(), senhaCripto, Dto.role());

            this.userRepository.save(newUser);

            return ResponseEntity.ok("Usuario Criado Com Sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Usuario não foi criado com sucesso " + e);

        }
    }


}
