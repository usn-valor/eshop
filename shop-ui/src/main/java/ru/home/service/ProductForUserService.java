package ru.home.service;

import ru.home.repr.ProductForUserRepr;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductForUserService {

    List<ProductForUserRepr> findAll();

    Optional<ProductForUserRepr> findById(Long id);

    void deleteById(Long id);

    void save(ProductForUserRepr product) throws IOException;
}
