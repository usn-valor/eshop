package ru.home.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.persist.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

