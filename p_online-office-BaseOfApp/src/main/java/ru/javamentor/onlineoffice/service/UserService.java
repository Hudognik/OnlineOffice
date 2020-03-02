package ru.javamentor.onlineoffice.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamentor.onlineoffice.entity.User;
import ru.javamentor.onlineoffice.exception.UserAlreadyExistsException;
import ru.javamentor.onlineoffice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean save(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            throw new UserAlreadyExistsException("Username " + user.getUsername() + " already exists");
        }
        update(user);
        return true;
    }

    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User get(Long id) {
        final Optional<User> UserById = userRepository.findById(id);
        return UserById.orElse(null);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
