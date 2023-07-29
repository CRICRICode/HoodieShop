package Cricri.Shop.dto;

import Cricri.Shop.models.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class ProductDTO {
    private Long id;
    private String nome; //unique
    private double prezzo;
    private int qta;
    private String size;
    private String nomeBrand;
    private String url;

    public ProductDTO(){

    }

    public ProductDTO(Product product){
        id= product.getId();
        nome= product.getNome();
        prezzo= product.getPrezzo();
        qta= product.getQta();
        size= product.getSize().toUpperCase();
        nomeBrand= product.getBrand().getNome();
        url= product.getUrl();
    }
}
