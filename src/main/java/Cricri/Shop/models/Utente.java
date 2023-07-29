package Cricri.Shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.LinkedList;

@Entity
@Setter
@Getter
public class Utente {
    @GeneratedValue
    @Id
    private Long id;
    private String cognome;
    private String nome;
    private String email;  //unique
    @OneToMany(mappedBy = "utente", cascade=CascadeType.ALL) //obbligatorio il mapped by, perchè sulla ManyToOne non può essere messo
    /*
    Ovviamente sulla OneToMany non possiamo essere Owner perchè l'owner ha
    la foreign key dell'Owned; quindi qui non potremmo avere una lista
    di foreign key.
     */
    @JsonIgnore
    private Collection<Buy> ordini;
    @OneToOne(mappedBy = "utente", cascade=CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private Cart cart;
    @Version()
    private long version;
    /*
    L'utente non lo rendiamo owner della relazione con cart così
    che sia obbligatorio cancellare prima il cart e poi l'utente
    dal DB. Dunque non possiamo cancellare un utente se vi egli ha
    qualcosa nel cart.
     */
    public Utente(){}

    public Utente(String email, String cognome, String nome)
    {
        this.email=email;
        this.cognome=cognome;
        this.nome=nome;
        this.ordini=new LinkedList<Buy>();
        this.cart =null;
    }
}
