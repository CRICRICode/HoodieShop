package Cricri.Shop.dto;

import Cricri.Shop.models.Buy;
import Cricri.Shop.models.OrderDetail;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class OrderDTO {
    private Date data;
    private Collection<CartProductDTO> dettagli = new LinkedList<>();

    public OrderDTO(Buy buy)
    {
        data= buy.getData();
        for(OrderDetail orderDetail : buy.getDetails())
            dettagli.add(new CartProductDTO(orderDetail));
    }
}
