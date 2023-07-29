package Cricri.Shop.services;

import Cricri.Shop.models.*;
import Cricri.Shop.services.exception.*;
import Cricri.Shop.services.keycloak.NewKeycloakUser;
import Cricri.Shop.repositories.CartRepository;
import Cricri.Shop.repositories.BuyRepository;
import Cricri.Shop.repositories.ProductCartRepository;
import Cricri.Shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GeneralService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductCartRepository productCartRepository;
    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private ProductService productService;

    @Transactional(rollbackFor = {EmailAlreadyUsedException.class, InvalidUserException.class, KeycloakErrorRegisterUser.class})
    public Utente registraUtente(String email, String nome, String cognome, String password) throws EmailAlreadyUsedException, InvalidUserException
    {
        if(email == null)
            throw new InvalidUserException();
        if(userRepository.existsByEmail(email)) //Email = chiave identificativa
            throw new EmailAlreadyUsedException();
        Utente newUtente = new Utente(email.toLowerCase(), cognome.toLowerCase(), nome.toLowerCase());
        Cart cartDiNewUtente = new Cart();
        cartDiNewUtente.setUtente(newUtente);
        newUtente.setCart(cartDiNewUtente);
        userRepository.save(newUtente);


        NewKeycloakUser.addUser(newUtente,password); //aggiungiamo il nuovo utente su Keycloak con la password che sarà use case sensitive
        return newUtente;
    }//registraUtente

    @Transactional(readOnly=true)
    public Utente getUtenteFromEmail(String email) throws EmailNotFoundException
    {
        if(email==null)
            throw new EmailNotFoundException();
        Optional<Utente> utente = userRepository.findByEmail(email.toLowerCase());
        if(utente.isPresent())
            return utente.get();
        else
            throw new EmailNotFoundException();
    }//getUtenteFromEmail

    @Transactional(readOnly=true)
    public Cart getCarrelloFromUtente(String email, String nome) throws InvalidUserException
    {

        Utente utente= userRepository.findByEmailAndNome(email, nome);
        System.out.println(utente);
        if(utente==null)
            throw new InvalidUserException();
        Optional<Cart> carrello = cartRepository.findByUtente(utente);
        if(carrello.isPresent())
            return carrello.get();
        else
            throw new InvalidUserException();
    }

    @Transactional(readOnly=true)
    public List<ProductCart> getAllProdottiInCarrello(Cart cart)
    {
        Optional<Cart> carrelloT = cartRepository.findById(cart.getId());
        LinkedList<ProductCart> ret = new LinkedList<>();
        carrelloT.ifPresent(value -> ret.addAll(value.getProducts()));
        return ret;
    }//getAllProdottiInCarrello

    @Transactional(readOnly=true)
    public ProductCart getProdottoFromCarrello(Long prodId, Cart cart) throws InvalidProductException, CartNotFoundException
    {
        if(prodId==null)
            throw new InvalidProductException();
        if(cart ==null)
            throw new CartNotFoundException();
        return productCartRepository.findByProductIdAndCart(prodId,cart);

    }//getProdottoFromCarrello

    @Transactional(readOnly=true)
    public List<Buy> getOrdini(Utente utente) throws InvalidUserException {
        if(utente==null)
            throw new InvalidUserException();
        return buyRepository.findAllByUtente(utente);
    }//getOrdini
    @Transactional(readOnly = true)
    public List<Product> getProdottiCaricatiDaUtente(String email) throws EmailNotFoundException {
        if (email == null) {
            throw new EmailNotFoundException();
        }

        Optional<Utente> utenteOptional = userRepository.findByEmail(email.toLowerCase());
        if (!utenteOptional.isPresent()) {
            throw new EmailNotFoundException();
        }

        Utente utente = utenteOptional.get();
        List<Product> prodottiCaricati = new ArrayList<>();

        List<Product> allProducts = productService.getProdottiAll();
        for (Product product : allProducts) {
            if (product.getBrand().getUtente().equals(utente)) {
                prodottiCaricati.add(product);
            }
        }

        return prodottiCaricati;
    }//getOrdini
}
