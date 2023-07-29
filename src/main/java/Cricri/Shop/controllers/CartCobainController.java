package Cricri.Shop.controllers;

import Cricri.Shop.services.BuyService;
import Cricri.Shop.services.CartService;
import Cricri.Shop.services.GeneralService;
import Cricri.Shop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/cart")
public class CartCobainController {
    @Autowired
    GeneralService generalService;
    @Autowired
    ProductService productService;
    @Autowired
    CartService cartService;
    @Autowired
    BuyService buyService;

    @PreAuthorize("hasAuthority('utente')")
    @GetMapping("/getProduct")
    public ResponseEntity<?> addAProductToCart( ) {
        System.out.println("OUT TRY");
        return null;
    }//addProdottoInCarrello
}
