package ru.home.service;

import ru.home.persist.model.PictureData;
import ru.home.persist.model.Product;

import java.util.Optional;

public interface PictureService {

    Optional<String> getPictureContentTypeById(long id);

    Optional<byte[]> getPictureDataById(long id);

    PictureData createPictureData(byte[] picture);

    Optional<Product> getProductByPictureId(long id);

    void removePicture(long id);
}
