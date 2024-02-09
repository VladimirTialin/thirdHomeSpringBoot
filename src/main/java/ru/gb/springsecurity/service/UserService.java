package ru.gb.springsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gb.springsecurity.model.User;
import ru.gb.springsecurity.repository.UserRepository;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository usersRepository;
        public ResponseEntity<Object> getAllUsers() {
            List<User> dbUsers = usersRepository.findAll();
            if (dbUsers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(dbUsers, HttpStatus.OK);
        }
}

