package ru.home.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.home.controller.repr.ProductRepr;
import ru.home.persist.model.Brand;
import ru.home.persist.model.Category;
import ru.home.persist.model.Product;
import ru.home.persist.repo.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

public class ProductRepositoryTest {

    private ProductService productService;
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        productRepository = Mockito.mock(ProductRepository.class); // фиктивный тестируемый класс
        productService = new ProductService(productRepository);
    }

    @Test
    public void testFindById() {
        Category expectedCategory = new Category();
        expectedCategory.setId(1L);
        expectedCategory.setName("Category Name");

        Brand expectedBrand = new Brand();
        expectedBrand.setId(1L);
        expectedBrand.setName("Brand Name");

        Product expectedProduct = new Product(); // фиктивный объект, который возвращает фиктивный класс-репозиторий
        expectedProduct.setId(1L);
        expectedProduct.setName("Product Name");
        expectedProduct.setCategory(expectedCategory);
        expectedProduct.setBrand(expectedBrand);
        expectedProduct.setPictures(new ArrayList<>());
        expectedProduct.setPrice(new BigDecimal(12345));

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(expectedProduct)); /* благодаря when можно
        запросить из фиктивного репозитория фиктивный объект для последующего тестирования */

        Optional<ProductRepr> opt = productService.findById(1L);

        Assertions.assertTrue(opt.isPresent());
        Assertions.assertEquals(expectedProduct.getId(), opt.get().getId());
        Assertions.assertEquals(expectedProduct.getName(), opt.get().getName());
    }
}
