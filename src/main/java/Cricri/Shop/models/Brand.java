package Cricri.Shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.Collection;
import java.util.LinkedList;

@Entity
@Setter
@Getter
@ToString
public class Brand { //sarebbe la categoria
    @JsonIgnore
    @GeneratedValue
    @Id
    private Long id;
    private String nome;
    @OneToMany(mappedBy = "brand", cascade= CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private Collection<Product> product = new LinkedList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Utente utente;

    @JsonIgnore
    @Version
    private long version;

    public Brand(){}
    public Brand(Brand brand)
    {
        nome=brand.nome.toLowerCase();
        product =new LinkedList<Product>();
    }
    public Brand(String nome)
    {
        this.nome=nome;
        product =new LinkedList<Product>();
    }
}
