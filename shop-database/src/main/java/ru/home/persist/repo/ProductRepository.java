package ru.home.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.home.persist.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p left join fetch p.pictures")
    List<Product> findAllWithPictureFetch();
}
