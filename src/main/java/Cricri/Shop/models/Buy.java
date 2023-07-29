package Cricri.Shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

@Entity
@Setter
@Getter
@ToString
public class Buy {
    @JsonIgnore
    @GeneratedValue
    @Id
    private Long id;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @ManyToOne(optional = false)
    @JsonIgnore
    private Utente utente;
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Collection<OrderDetail> details = new LinkedList<>();
    @Version
    private long version;

    public Buy(){}
}
