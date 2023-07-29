package Cricri.Shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


@Entity
@Setter
@Getter

public class Cart {
    @GeneratedValue
    @Id
    private Long id;
    @OneToOne(optional = false)
    @JsonIgnore
    @ToString.Exclude  //sennò va in overflow
    private Utente utente;
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Collection<ProductCart> products = new LinkedList<>();
    @Version
    private long version; //sul carrello vi possono essere accessi concorrenti da parte dello stesso utente da due dispositivi diversi
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<ProductCart> productCartList;

    public Cart(){}

    // Metodo per ottenere la lista di ProductCart associati al carrello
    public List<ProductCart> getProductCartList() {
        return productCartList;
    }
}
