package Cricri.Shop.repositories;

import Cricri.Shop.models.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findByEmail(String email);

    Utente findByEmailAndNome(String email, String nome);
    boolean existsByEmail(String email);
}
