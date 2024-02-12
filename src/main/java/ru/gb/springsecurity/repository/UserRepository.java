package ru.gb.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.springsecurity.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByLogin(String login);
}
