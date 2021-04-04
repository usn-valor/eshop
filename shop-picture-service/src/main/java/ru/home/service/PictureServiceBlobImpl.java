package ru.home.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.home.persist.model.Picture;
import ru.home.persist.model.PictureData;
import ru.home.persist.model.Product;
import ru.home.persist.repo.PictureRepository;

import java.util.Optional;

@Service
public class PictureServiceBlobImpl implements PictureService {

    private final PictureRepository repository;

    @Autowired
    public PictureServiceBlobImpl(PictureRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<String> getPictureContentTypeById(long id) {
        return repository.findById(id)
                .filter(pic -> pic.getPictureData().getData() != null)
                .map(Picture::getContentType);
    }

    @Override
    public Optional<byte[]> getPictureDataById(long id) {
        return repository.findById(id)
                .filter(pic -> pic.getPictureData().getData() != null)
                .map(pic -> pic.getPictureData().getData());
    }

    @Override
    public PictureData createPictureData(byte[] picture) {
        return new PictureData(picture);
    }

    @Override
    public Optional<Product> getProductByPictureId(long id) {
        return repository.findById(id)
                .map(Picture::getProduct);
    }

    @Override
    @Transactional
    public void removePicture(long id) {
        repository.deleteById(id);
    }
}
