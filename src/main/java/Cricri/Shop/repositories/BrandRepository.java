package Cricri.Shop.repositories;

import Cricri.Shop.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    boolean existsByNome(String nome);
    Brand findByNome(String nome);
}
