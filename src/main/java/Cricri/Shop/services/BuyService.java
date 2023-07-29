package Cricri.Shop.services;

import Cricri.Shop.dto.ProductDTO;
import Cricri.Shop.services.exception.*;
import Cricri.Shop.models.*;
import Cricri.Shop.repositories.*;
import Cricri.Shop.services.exception.PriceChangedException;
import Cricri.Shop.services.exception.ProductNotFoundException;

import jakarta.persistence.OptimisticLockException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional()
public class BuyService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private ProductCartRepository productCartRepository;
    @Transactional(rollbackFor = {CartNotFoundException.class, InvalidUserException.class, OutOfStockException.class,PriceChangedException.class,NotEnoughtProductsException.class, OptimisticLockException.class})
    public boolean acquista(Cart cart, Utente utente, List<ProductDTO> listaProdotti)
            throws CartNotFoundException, InvalidUserException, OutOfStockException, PriceChangedException, NotEnoughtProductsException
    {
        if(listaProdotti.isEmpty())
            return true; //lista prodotti vuota
        if(cart == null)
            throw new CartNotFoundException();

        Cart cCart = cartRepository.findCartByUtenteId(utente.getId());

        if(cCart==null)
            throw new CartNotFoundException();

        Buy buy = new Buy();
        Utente utenteT = userRepository.findByEmailAndNome(utente.getEmail(), utente.getNome());
        if(utenteT==null)
            throw new InvalidUserException();
        buy.setUtente(utenteT);
        //data autogenerata sull'buy
        buyRepository.save(buy);
        for(ProductDTO productDTO : listaProdotti)
        {
            Product prodottoNelDB = productRepository.findByNomeAndSize(productDTO.getNome(), productDTO.getSize().toLowerCase());
            System.out.println(prodottoNelDB);
            if(prodottoNelDB==null)
                throw new OutOfStockException("Articolo: "+ productDTO.getNome().toLowerCase()+" esaurito.");
            if(productDTO.getPrezzo() != prodottoNelDB.getPrezzo())
                throw new PriceChangedException("Vecchio prezzo: "+ productDTO.getPrezzo()+
                        " cambiato in nuovo prezzo: "+prodottoNelDB.getPrezzo());
            if(productDTO.getQta()>prodottoNelDB.getQta())
                throw new NotEnoughtProductsException(productDTO.getNome());
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(prodottoNelDB);
            orderDetail.setPrezzo(productDTO.getPrezzo());
            orderDetail.setQta(productDTO.getQta());
            orderDetailsRepository.save(orderDetail);
            buy.getDetails().add(orderDetail);
            prodottoNelDB.setQta(prodottoNelDB.getQta()- productDTO.getQta());
        }
        return true;
    }//acquista
    @Transactional(rollbackFor = {CartNotFoundException.class, ProductNotFoundException.class,  OptimisticLockException.class})
    public void svuotaCarrello(Cart cart)
    throws CartNotFoundException, ProductNotFoundException
    {
        if(cart == null)
            throw new CartNotFoundException();
        Optional<Cart> carrelloT = cartRepository.findById(cart.getId());
        if(! carrelloT.isPresent())
            throw new CartNotFoundException();

        LinkedList<ProductCart> listaT = new LinkedList<>(carrelloT.get().getProducts()); //per evitare una concurrent modification exception
        for(ProductCart productCart : listaT)
        {
            Optional<ProductCart> prodottoT =
                    productCartRepository.findById(productCart.getId());
            if(! prodottoT.isPresent())
                throw new ProductNotFoundException();
            carrelloT.get().getProducts().remove((prodottoT.get()));
        }
        cartRepository.save(carrelloT.get());

        productCartRepository.deleteAll(listaT);

    }//svuotaCarrello
}
