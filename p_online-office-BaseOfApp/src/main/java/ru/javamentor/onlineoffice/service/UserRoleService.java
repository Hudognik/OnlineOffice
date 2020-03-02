package ru.javamentor.onlineoffice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.onlineoffice.entity.UserRole;
import ru.javamentor.onlineoffice.repository.UserRoleRepository;

import java.util.List;

@Service
@Transactional
public class UserRoleService {

    private final UserRoleRepository repo;

    public UserRoleService(UserRoleRepository repo) {
        this.repo = repo;
    }

    public void save(UserRole role) {
        repo.save(role);
    }

    public List<UserRole> listAll() {
        return repo.findAll();
    }

    public UserRole get(Long id) {
        return repo.findById(id).get();
    }

    public UserRole findByRole(String role) {
        return repo.findByRole(role);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
