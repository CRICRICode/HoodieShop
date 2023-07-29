package Cricri.Shop.services;

import Cricri.Shop.models.Product;
import Cricri.Shop.services.exception.*;
import Cricri.Shop.models.Brand;
import Cricri.Shop.repositories.BrandRepository;
import Cricri.Shop.repositories.ProductRepository;
import Cricri.Shop.services.exception.ProductAlreadyExistsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;


    @Transactional(readOnly=true)
    public List<Product> getAllProdottiInPagina(int pageNumber, int pageSize, String sortBy)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy.toLowerCase()));
        Page<Product> pages= productRepository.findAll(pageable);
        if( pages.hasContent())
            return pages.getContent();
        else
            return new LinkedList<>();
    }//getAllProdottiInPagina

    @Transactional(readOnly=true)
    public List<Product> getProdottiAll()
    {
        return productRepository.findAll();
    }

    @Transactional(readOnly=true)
    public Optional<Product> getProdottoById(long id)
    {
        return productRepository.findById(id);
    }

    @Transactional(readOnly=true)
    public Product getProdottoByNomeAndSize(String nome, String size) throws ProductNotFoundException
    {
        if(nome==null)
            throw new ProductNotFoundException();
        return productRepository.findProdottoByNomeAndSize(nome, size);
    }//getProdottoByNomeAndSize

    @Transactional(readOnly=true)
    public List<Product> getListaFiltrata(String nome)
    {
        return productRepository.findByNomeContaining(nome);
    }//getListaFiltrati
    public Product caricaProdotto(Product product) throws BrandNotFoundException, ProductNotFoundException, ProductAlreadyExistsException, InvalidProductException
    {
        Brand brand = product.getBrand();
        if(brand == null || ! brandRepository.existsByNome(brand.getNome().toLowerCase()))
            throw new BrandNotFoundException();
        brand = brandRepository.findByNome(brand.getNome().toLowerCase());
        if(product.getNome() == null || productRepository.existsByNomeAndSize(product.getNome(), product.getSize()))
            throw new ProductAlreadyExistsException();
        if(product.getQta() < 1 || product.getPrezzo()<=0.0)
            throw new InvalidProductException();

        Product productT =new Product(product);
        productT.setBrand(brand);

        brand.getProduct().add(productT);  //lavoriamo sulla versione T di brand perchè altrimenti
        // potremmo sovrascrivere quello passato come parametro e così perderemmo il dato
        return productRepository.save(productT);
    }//caricaProdotto
    @Transactional(rollbackFor = {BrandAlreadyExistsException.class, BrandNotSupportedException.class})
    public Brand caricaBrand(Brand brand) throws BrandNotSupportedException, BrandAlreadyExistsException
    {
        if(brand.getProduct()!=null)
            if(! brand.getProduct().isEmpty())
                throw new BrandNotSupportedException();
        if(brandRepository.existsByNome(brand.getNome().toLowerCase()))
            throw new BrandAlreadyExistsException();

        Brand brandT=new Brand(brand);

        return brandRepository.save(brandT);
    }//caricaBrand
}
