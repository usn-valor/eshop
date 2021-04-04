package ru.home.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.persist.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
