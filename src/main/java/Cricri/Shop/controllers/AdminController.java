package Cricri.Shop.controllers;

import Cricri.Shop.dto.ProductDTO;
import Cricri.Shop.models.Brand;
import Cricri.Shop.models.Product;
import Cricri.Shop.services.ProductService;
import Cricri.Shop.services.exception.BrandAlreadyExistsException;
import Cricri.Shop.services.exception.BrandNotFoundException;
import Cricri.Shop.services.exception.InvalidProductException;
import Cricri.Shop.services.exception.ProductAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value= "/admin")
public class AdminController {
    @Autowired

    ProductService productService;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/prodotto")
    public ResponseEntity<?> caricaProdotto(@RequestBody ProductDTO prodotto) {
        Product ret = null;
        try {
            System.out.println("prodotto");
            Product tmp = new Product();
            tmp.setQta(prodotto.getQta());
            tmp.setPrezzo(prodotto.getPrezzo());
            tmp.setNome(prodotto.getNome());
            tmp.setSize(prodotto.getSize().toLowerCase());
            tmp.setBrand(new Brand(prodotto.getNomeBrand()));
            tmp.setUrl(prodotto.getUrl());
            ret = productService.caricaProdotto(tmp);
        } catch (BrandNotFoundException bnfe) {
            return new ResponseEntity<>("Brand not found.", HttpStatus.NOT_FOUND);
        } catch (ProductAlreadyExistsException paee) {
            return new ResponseEntity<>("Product already exists.", HttpStatus.NOT_ACCEPTABLE);
        } catch (InvalidProductException ipe) {
            return new ResponseEntity<>("Quantity or Price out of bound.", HttpStatus.NOT_ACCEPTABLE);
        } catch (OptimisticLockingFailureException olfe) {
            return new ResponseEntity<>("L'operazione non è andata a buon fine", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ProductDTO productDTO = new ProductDTO(ret);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("brand")
    public ResponseEntity<String> caricaBrand(@RequestBody String nomeBrand)
    {
        Brand ret = null; //Non ci saranno casi in cui
        try{
            Brand tmp=new Brand(nomeBrand);
            ret = productService.caricaBrand(tmp);
        }catch(BrandAlreadyExistsException baee){
            return new ResponseEntity<>("Brand already exists.", HttpStatus.NOT_ACCEPTABLE);
        }catch(OptimisticLockingFailureException olfe)
        {
            return new ResponseEntity<>("L'operazione non è andata a buon fine", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ret.getNome(), HttpStatus.OK);
    }


}
