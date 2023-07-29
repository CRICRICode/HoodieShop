package Cricri.Shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
public class OrderDetail {
    @JsonIgnore
    @GeneratedValue
    @Id
    private Long id;
    private Integer qta;
    private Double prezzo;
    @ManyToOne(optional = false)
    private Product product;
    @JsonIgnore
    @Version
    private long version;

    public OrderDetail(){}
}
