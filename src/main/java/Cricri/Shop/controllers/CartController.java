package Cricri.Shop.controllers;

import Cricri.Shop.dto.CartProductDTO;
import Cricri.Shop.dto.ProductDTO;
import Cricri.Shop.models.Cart;
import Cricri.Shop.models.ProductCart;
import Cricri.Shop.models.Product;
import Cricri.Shop.services.exception.*;
import Cricri.Shop.services.keycloak.Utils;
import Cricri.Shop.models.Utente;
import Cricri.Shop.services.BuyService;
import Cricri.Shop.services.CartService;
import Cricri.Shop.services.ProductService;
import Cricri.Shop.services.GeneralService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping(value= "/carrello")
public class CartController {
    @Autowired
    GeneralService generalService;
    @Autowired
    ProductService productService;
    @Autowired
    CartService cartService;
    @Autowired
    BuyService buyService;


    @PreAuthorize("hasAuthority('utente')")
    @GetMapping()
    public ResponseEntity<?> getProdottiInCarrello(@RequestParam(value= "pageNumber", defaultValue="0") int pageNumber,
                                                                      @RequestParam(value= "pageSize", defaultValue="10") int pageSize,
                                                                      @RequestParam(value= "sortBy", defaultValue="prezzo") String sortBy)
    {
        Cart cart =null;
        cart = generalService.getCarrelloFromUtente(Utils.getEmail(), Utils.getName());

        List<ProductCart> result = cart.getProductCartList();

        List<CartProductDTO> ret =new LinkedList<>();
        for(ProductCart prod : result)
            ret.add(new CartProductDTO(prod));

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }//getProdottiInCarrello


    @PreAuthorize("hasAuthority('utente')")
    @PostMapping()
    public ResponseEntity<?> aggiungiProdotto( @RequestBody ProductDTO product) {
        System.out.println("OUT TRY");
        try { System.out.println("IN TRY");
            Cart cart = cartService.addProductCart(Utils.getEmail(), product, product.getQta(), Utils.getName());
            //CartProductDTO cartProductDTO = new CartProductDTO(cart.getProductCartList()); // Assumi che il carrello contenga una lista di ProductCart
            List<CartProductDTO> cartProductDTOList= new LinkedList<>();
            if (cart.getProducts().size()>0){
                for (ProductCart product1:cart.getProducts()) {
                    cartProductDTOList.add(new CartProductDTO(product1));
                }
            }
            return new ResponseEntity<>(cartProductDTOList, HttpStatus.OK);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User non esiste!", e);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il Prodotto non esiste!", e);
        }
    }//addProdottoInCarrello

    @PreAuthorize("hasAuthority('utente')")
    @PostMapping("/remove")  //prima era delete, ma da angular è più comodo usare il post
    public ResponseEntity<String> removeProdottoInCarrello(@RequestBody ProductDTO prodotto) //il ProductDTO ha la quantità da voler rimuovere
    {
        Cart cart =null;
        ProductCart prodottoDaRimuovere=null;
        try {
            Utente utente = generalService.getUtenteFromEmail(Utils.getEmail());
            cart = generalService.getCarrelloFromUtente(Utils.getEmail(), Utils.getName());
            System.out.println(cart);
            ProductCart prodottoTMP = generalService.getProdottoFromCarrello(prodotto.getId(), cart);
            if(prodottoTMP==null)
                return new ResponseEntity<>("Product non presente nel cart", HttpStatus.NOT_FOUND);
            prodottoDaRimuovere = prodottoTMP;
        }catch(InvalidProductException ipe)
        {
            return new ResponseEntity<>("Product non presente nel cart", HttpStatus.NOT_FOUND);
        }catch(CartNotFoundException cnfe)
        {
            return new ResponseEntity<>("Cart non trovato", HttpStatus.NOT_FOUND);
        }catch(OptimisticLockingFailureException olfe)
        {
            return new ResponseEntity<>("L'operazione non è andata a buon fine", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try{
            Cart ret = cartService.rimuoviProdottoDalCarrello( prodottoDaRimuovere,cart ,prodotto.getQta());
            return new ResponseEntity<>("Cart: "+ret, HttpStatus.OK);
        }catch(ProductNotFoundException pnfe){
            return new ResponseEntity<>("Il prodotto è già stato eliminato", HttpStatus.NOT_FOUND);
        }catch(CartNotFoundException cnfe){
            return new ResponseEntity<>("Cart non trovato", HttpStatus.NOT_FOUND);
        }catch(InvalidProductException ipe){
            return new ResponseEntity<>("Product non presente nel cart", HttpStatus.NOT_FOUND);
        }catch(OptimisticLockingFailureException olfe)
        {
            return new ResponseEntity<>("L'operazione non è andata a buon fine", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }//removeProdottoInCarrello

    @PreAuthorize("hasAuthority('utente')")
    @PostMapping(value="/acquista")
    public ResponseEntity<?> acquista(@RequestBody List<ProductDTO> listaProdotti)
    {
        try{
            System.out.println("TRY IN");
            Utente utente = generalService.getUtenteFromEmail(Utils.getEmail());
            Cart cart = generalService.getCarrelloFromUtente(Utils.getEmail(), Utils.getName());
            boolean esito = buyService.acquista(cart, utente, listaProdotti);
            System.out.println(esito);


            buyService.svuotaCarrello(cart);
            if(esito)
                return new ResponseEntity<>("Buy andato a buon fine. Riepilogo acquisto: "+listaProdotti, HttpStatus.OK);
        }catch(CartNotFoundException cnfe)
        {
            return new ResponseEntity<>("I prodotti selezionati non appartengono al carrello utente.", HttpStatus.NOT_FOUND);
        }catch(OutOfStockException pee){
            return new ResponseEntity<>(pee.getMessage(), HttpStatus.NOT_FOUND);
        }catch(PriceChangedException pce){
            return new ResponseEntity<>(pce.getMessage(), HttpStatus.NOT_FOUND);
        }catch(NotEnoughtProductsException nep){
            return new ResponseEntity<>("Non ci sono abbastanza articoli di tipo: "+nep.getMessage(), HttpStatus.NOT_FOUND);
        }catch(OptimisticLockingFailureException olfe)
        {
            return new ResponseEntity<>("L'operazione non è andata a buon fine", HttpStatus.INTERNAL_SERVER_ERROR);
        }//try catch
    return new ResponseEntity<>("Buy dei prodotti: "+listaProdotti+" \n fallito. Riprovare", HttpStatus.PERMANENT_REDIRECT);
    }//acquista
}
