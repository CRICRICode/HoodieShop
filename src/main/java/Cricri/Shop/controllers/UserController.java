package Cricri.Shop.controllers;

import Cricri.Shop.dto.RegistrationDTO;
import Cricri.Shop.dto.OrderDTO;
import Cricri.Shop.models.Buy;
import Cricri.Shop.services.exception.EmailAlreadyUsedException;
import Cricri.Shop.services.exception.InvalidUserException;
import Cricri.Shop.services.keycloak.Utils;
import Cricri.Shop.models.Utente;
import Cricri.Shop.services.CartService;
import Cricri.Shop.services.ProductService;
import Cricri.Shop.services.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value= "/utenti")
public class UserController {
    @Autowired
    CartService cartService;
    @Autowired
    GeneralService generalService;
    @Autowired
    ProductService productService;

    @PostMapping(value="/registrazione")
    public ResponseEntity<String> registrazione(@RequestBody RegistrationDTO utente)  //usiamo un DTO specifico
    {
        Utente newUtente=null;
        try{
            newUtente = generalService.registraUtente(utente.getEmail(), utente.getNome(), utente.getCognome(), utente.getPassword());
        }catch(EmailAlreadyUsedException eaue){
            return new ResponseEntity("Registrazione annullata, utente già esistente.", HttpStatus.NOT_ACCEPTABLE);
        }catch(InvalidUserException iue)
        {
            return new ResponseEntity("Email non valida.", HttpStatus.NOT_ACCEPTABLE);
        }catch(OptimisticLockingFailureException olfe)
        {
            return new ResponseEntity<>("L'operazione non è andata a buon fine", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity("Registrazione andata a buon fine. Utente Registrato: " + newUtente, HttpStatus.OK);

    }//registrazione

    @PreAuthorize("hasAuthority('utente')")
    @GetMapping(value="/ordini")
    public ResponseEntity<List<OrderDTO>> getListaOrdini()
     {
         try{
             Utente utente = generalService.getUtenteFromEmail(Utils.getEmail());
             List<Buy> listaOrdini = generalService.getOrdini(utente);

             List<OrderDTO> ret =new LinkedList<>();
             for(Buy buy : listaOrdini)
                 ret.add(new OrderDTO(buy));

             return new ResponseEntity<>(ret, HttpStatus.OK);
         }catch(InvalidUserException iue){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
         }
     }
}
