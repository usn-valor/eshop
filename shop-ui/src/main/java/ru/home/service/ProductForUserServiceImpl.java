package ru.home.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.home.error.SWWException;
import ru.home.persist.model.Picture;
import ru.home.persist.model.Product;
import ru.home.persist.repo.ProductRepository;
import ru.home.repr.ProductForUserRepr;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductForUserServiceImpl implements ProductForUserService, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ProductForUserServiceImpl.class);

    private final ProductRepository productRepository;

    private final PictureService pictureService;

    @Autowired
    public ProductForUserServiceImpl(ProductRepository productRepository, PictureService pictureService) {
        this.productRepository = productRepository;
        this.pictureService = pictureService;
    }

    @Override
    @Transactional
    public List<ProductForUserRepr> findAll() {
        return productRepository.findAllWithPictureFetch().stream()
                .map(ProductForUserRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<ProductForUserRepr> findById(Long id) {
        return productRepository.findById(id).map(ProductForUserRepr::new);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(ProductForUserRepr productRepr) throws IOException {
        Product product = (productRepr.getId() != null) ? productRepository.findById(productRepr.getId())
                .orElseThrow(SWWException::new) : new Product();
        product.setName(productRepr.getName());
        product.setCategory(productRepr.getCategory());
        product.setBrand(productRepr.getBrand());
        product.setPrice(productRepr.getPrice());

        if (productRepr.getNewPictures() != null) {
            for (MultipartFile newPicture : productRepr.getNewPictures()) {
                logger.info("Product {} file {} size {} contentType {}", productRepr.getId(),
                        newPicture.getOriginalFilename(), newPicture.getSize(), newPicture.getContentType());

                if (product.getPictures() == null) {
                    product.setPictures(new ArrayList<>());
                }

                product.getPictures().add(new Picture(
                        newPicture.getOriginalFilename(),
                        newPicture.getContentType(),
                        pictureService.createPictureData(newPicture.getBytes()),
                        product
                ));
            }
        }

        productRepository.save(product);
    }
}
