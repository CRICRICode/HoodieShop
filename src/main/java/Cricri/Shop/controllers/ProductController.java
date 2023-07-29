package Cricri.Shop.controllers;

import Cricri.Shop.dto.ProductDTO;
import Cricri.Shop.dto.RegistrationDTO;
import Cricri.Shop.models.Product;
import Cricri.Shop.services.GeneralService;
import Cricri.Shop.services.ProductService;
import Cricri.Shop.services.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin("*")
@RequestMapping(value= "/prodotti")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    GeneralService generalService;

    /* @GetMapping("/prova")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<Product>> getProdottiProva(){
        List<Product> a = prodottoService.getProdottiAll();
        return new ResponseEntity<>(a, HttpStatus.OK);
    }*/

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProdotti(@RequestParam(value= "pageNumber", defaultValue="0") int pageNumber,
                                                           @RequestParam(value= "pageSize", defaultValue="10") int pageSize,
                                                           @RequestParam(value= "sortBy", defaultValue="id") String sortBy)
    {
        List<Product> result= productService.getAllProdottiInPagina(pageNumber, pageSize, sortBy);
        List<ProductDTO> ret =new LinkedList<>();
        for(Product prod : result)
            ret.add(new ProductDTO(prod));

       return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProdottoById(@PathVariable("id") long id)
    {
       Optional<Product> ret = productService.getProdottoById(id);
       if(ret.isPresent())
           return new ResponseEntity<>(new ProductDTO(ret.get()), HttpStatus.OK);
       else
           return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/filtrati")
    public ResponseEntity<List<ProductDTO>> getProdottiFiltrati(@RequestParam(value= "nome", defaultValue="") String nome)
    {
        List<Product> result= productService.getListaFiltrata(nome);
        System.out.println(result.size());
        List<ProductDTO> ret =new LinkedList<>();

        for(Product prod : result)
            ret.add(new ProductDTO(prod));
        System.out.println(ret.size());
        return new ResponseEntity<>(ret, HttpStatus.OK);

    }


    @GetMapping("/{userId}")
    public List<Product> getProdottiCaricatiDaUtente(@RequestBody RegistrationDTO utente) {
        try {
            return generalService.getProdottiCaricatiDaUtente(utente.getEmail());
        } catch (InvalidUserException e) { // Gestisco l'eccezione se l'utente non viene trovato
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
