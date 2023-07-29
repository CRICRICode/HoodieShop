package Cricri.Shop.repositories;

import Cricri.Shop.models.Cart;
import Cricri.Shop.models.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {
    ProductCart findByProductIdAndCart(Long idProd, Cart cart);


}
