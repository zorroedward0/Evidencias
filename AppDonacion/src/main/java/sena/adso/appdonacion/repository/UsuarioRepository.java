package sena.adso.appdonacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import sena.adso.appdonacion.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

}