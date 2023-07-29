package Cricri.Shop.repositories;

import Cricri.Shop.models.Buy;
import Cricri.Shop.models.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long> {

    Optional<Buy> findByUtente(Utente utente);

    List<Buy> findAllByUtente(Utente utente);
}
