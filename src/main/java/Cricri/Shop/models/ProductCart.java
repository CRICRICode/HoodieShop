package Cricri.Shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Setter
@Getter
public class ProductCart
{
    @JsonIgnore
    @GeneratedValue
    @Id
    private Long id;
    private Integer qta;
    private Double prezzo;
    @ManyToOne(optional = false)  //il cascade tipe qui potrebbe essere pericoloso, perchè propaga le modifiche sui product visti da tutti gli utenti
    private Product product;
    @JsonIgnore
    @Version
    private long version;
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn() // Questa annotazione specifica il nome della colonna per la relazione con Cart
    @ToString.Exclude
    private Cart cart;


    public ProductCart(){}

    public ProductCart(Product product)
    {
        qta= product.getQta()-(product.getQta()-1); // ==1
        prezzo= product.getPrezzo();
        this.product = product;
    }
}
