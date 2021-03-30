package ru.home.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.persist.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
