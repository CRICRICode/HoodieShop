package Cricri.Shop.repositories;

import Cricri.Shop.models.Cart;
import Cricri.Shop.models.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUtente(Utente utente);

    Cart findCartByUtenteId(Long utenteId);
}
