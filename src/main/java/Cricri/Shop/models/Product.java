package Cricri.Shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jboss.resteasy.spi.touri.MappedBy;
import org.springframework.lang.Nullable;

import java.util.List;

@Entity
@Setter
@Getter
public class Product {
    @JsonIgnore
    @GeneratedValue
    @Id
    private Long id;
    private String nome; //unique
    private double prezzo;
    private int qta;
    private String size;
    @Nullable
    private String url;
    @ManyToOne(optional = false, cascade=CascadeType.ALL)
    private Brand brand;
    @JsonIgnore
    @Version
    private long version;


    public Product(){}
    public Product(Product product) {
        nome = product.nome;
        prezzo = product.prezzo;
        qta = product.qta;
        size = product.size;
        url = product.url;
        brand = new Brand(product.brand);  //poi viene sovrascritto nei service
    }
}
