package ru.home.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.home.controller.repr.ProductRepr;
import ru.home.persist.model.Picture;
import ru.home.persist.model.Product;
import ru.home.persist.repo.ProductRepository;
import ru.home.persist.repo.ProductSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<ProductRepr> findById(Long id) {
        return productRepository.findById(id)
                .map(ProductService::mapToRepr);
    }

    public Page<ProductRepr> findByFilter(Long categoryId, Integer page, Integer size) {
        Specification<Product> spec = Specification.where(null); // ProductSpecification.fetchPictures();
        if (categoryId != null) {
            spec = spec.and(ProductSpecification.byCategory(categoryId));
        }
        Page<Long> ids = productRepository.findAll(spec, PageRequest.of(page - 1, size))
                .map(Product::getId);

        List<ProductRepr> allByIds = productRepository.findAllByIds(ids.getContent()).stream()
                .map(ProductService::mapToRepr)
                .collect(Collectors.toList());
        return new PageImpl<>(allByIds, PageRequest.of(page - 1, size), ids.getTotalElements());
    }

    private static ProductRepr mapToRepr(Product p) {
        return new ProductRepr(
                p.getId(),
                p.getName(), p.getPrice(),
                p.getPictures().size() > 0 ? p.getPictures().get(0).getId() : null,
                p.getPictures().stream().map(Picture::getId).collect(Collectors.toList())
        );
    }
}
