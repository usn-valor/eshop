package ru.home.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.persist.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
