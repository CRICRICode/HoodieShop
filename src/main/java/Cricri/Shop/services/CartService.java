package Cricri.Shop.services;

import Cricri.Shop.dto.CartProductDTO;
import Cricri.Shop.dto.ProductDTO;
import Cricri.Shop.models.Cart;
import Cricri.Shop.models.Product;
import Cricri.Shop.models.ProductCart;
import Cricri.Shop.models.Utente;
import Cricri.Shop.repositories.UserRepository;
import Cricri.Shop.services.exception.*;
import Cricri.Shop.repositories.CartRepository;
import Cricri.Shop.repositories.ProductCartRepository;
import Cricri.Shop.repositories.ProductRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductCartRepository productCartRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = {UsernameNotFoundException.class,ProductNotFoundException.class, OptimisticLockException.class},propagation = Propagation.NESTED)
    public Cart addProductCart(String email, ProductDTO idProd, int quantity, String nome) throws UsernameNotFoundException, ProductNotFoundException {
        //Controllo la quantità
        //if(quantity<=0)
          //  throw new QuantityMustBePositiveAndGTZero();//da mettere eccezioni stesse del rollback
        //Trovo l'utente
        System.out.println("ADDPRODUCTCART");
        Utente user = userRepository.findByEmailAndNome(email, nome);
        if (user == null) {
            throw new EmailNotFoundException();
        }
        //Trovo il prodotto
        Product product = productRepository.findById(idProd.getId()).orElseThrow(ProductNotFoundException::new);
        System.out.println(product);
        //Mi trovo il carrello associato all'utente
        Cart cart = cartRepository.findCartByUtenteId(user.getId());
        //Trovo il prodotto nel carrello
        ProductCart producInCart = productCartRepository.findByProductIdAndCart(idProd.getId(),cart);

        if(producInCart == null){
            System.out.println("Nuovo carello");
            //Il prodotto non è presente quindi lo creo nuovo
            producInCart = new ProductCart();
            producInCart.setQta(quantity);
            producInCart.setProduct(product);
            producInCart.setCart(cart);
            producInCart.setPrezzo(product.getPrezzo());
        }else{
            //Il prodotto è presente e aggiorno la quantitò
            System.out.println("Aggiorno il carrello");
            int updatedQuantity = producInCart.getQta()+quantity;
            producInCart.setQta(updatedQuantity);
        }

        productCartRepository.save(producInCart);
        return cartRepository.save(cart);
    }//addProductCart


    @Transactional(rollbackFor = {ProductNotFoundException.class, UsernameNotFoundException.class, CartNotFoundException.class, InvalidProductException.class,  OptimisticLockException.class})
    public Cart rimuoviProdottoDalCarrello(ProductCart prodottoDaRimuovere, Cart cart, int qta)
    throws ProductNotFoundException, CartNotFoundException, InvalidProductException
    {//per ipotesi il prodotto appartiene al cart

        //Trovo il prodotto nel carrello
        ProductCart producInCart = productCartRepository.findByProductIdAndCart(prodottoDaRimuovere.getProduct().getId(),cart);

        if(producInCart != null) {
            System.out.println("Aggiorno il carrello");
            int updatedQuantity = producInCart.getQta()-qta;
            producInCart.setQta(updatedQuantity);
            productCartRepository.save(producInCart);
        }
        if(producInCart.getQta()==0){
            productCartRepository.delete(producInCart);
        }


        Cart ret = cartRepository.save(cart);
        return ret;
    } //rimuoviProdottoDalCarrello
}
