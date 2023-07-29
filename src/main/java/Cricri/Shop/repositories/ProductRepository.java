package Cricri.Shop.repositories;

import Cricri.Shop.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByNomeAndSize(String nome, String size);

    Product findProdottoByNomeAndSize(String nome, String size);

    Product findByNomeAndSize(String nome, String size );
    List<Product> findByNomeContaining(String name);

}

