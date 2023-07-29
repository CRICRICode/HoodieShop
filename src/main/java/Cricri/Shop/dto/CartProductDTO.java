package Cricri.Shop.dto;

import Cricri.Shop.models.Cart;
import Cricri.Shop.models.ProductCart;
import Cricri.Shop.models.OrderDetail;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class CartProductDTO {
    private Long id;
    private int qta;
    private double prezzo;
    private ProductDTO prodotto;

    public CartProductDTO() {
    }

    public CartProductDTO(ProductCart prodotto) {
        prezzo = prodotto.getPrezzo();
        qta = prodotto.getQta();
        this.prodotto = new ProductDTO(prodotto.getProduct());
    }

    // Aggiungi un nuovo costruttore per accettare una lista di ProductCart
    public CartProductDTO(List<ProductCart> productCartList) {
        if (productCartList != null && !productCartList.isEmpty()) {
            // Assumi che i ProductCart siano gli stessi per tutti gli elementi nella lista
            ProductCart firstProductCart = productCartList.get(0);
            prezzo = firstProductCart.getPrezzo();
            qta = firstProductCart.getQta();
            this.prodotto = new ProductDTO(firstProductCart.getProduct());
        }
    }

    // Aggiungi un metodo statico per convertire una lista di ProductCart in una lista di CartProductDTO
    public static List<CartProductDTO> fromProductCartList(List<ProductCart> productCartList) {
        return productCartList.stream()
                .map(CartProductDTO::new)
                .collect(Collectors.toList());
    }

    public CartProductDTO(OrderDetail prodotto) {
        prezzo = prodotto.getPrezzo();
        qta = prodotto.getQta();
        this.prodotto = new ProductDTO(prodotto.getProduct());
    }
}
