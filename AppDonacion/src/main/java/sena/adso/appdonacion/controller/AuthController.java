package sena.adso.appdonacion.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sena.adso.appdonacion.model.Usuario;
import sena.adso.appdonacion.repository.UsuarioRepository;
import sena.adso.appdonacion.service.JwtService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final UsuarioRepository repository;
    private final JwtService jwtService;

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public AuthController(
            UsuarioRepository repository,
            JwtService jwtService
    ) {

        this.repository = repository;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {

        usuario.setPassword(
                encoder.encode(usuario.getPassword())
        );

        usuario.setRole("USER");

        return repository.save(usuario);
    }

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody Usuario request
    ) {

        Usuario usuario = repository.findByUsername(
                request.getUsername()
        ).orElseThrow();

        boolean ok = encoder.matches(
                request.getPassword(),
                usuario.getPassword()
        );

        if (!ok) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtService.generateToken(
                usuario.getUsername()
        );

        return Map.of(
                "token",
                token
        );
    }
}
