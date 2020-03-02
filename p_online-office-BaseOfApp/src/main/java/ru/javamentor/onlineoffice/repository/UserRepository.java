package ru.javamentor.onlineoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javamentor.onlineoffice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
