package ru.javamentor.onlineoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javamentor.onlineoffice.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole (String role);
}