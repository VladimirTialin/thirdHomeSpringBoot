package ru.gb.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import ru.gb.springsecurity.model.ReaderProperties;
import ru.gb.springsecurity.model.Role;
import ru.gb.springsecurity.model.User;
import ru.gb.springsecurity.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(ReaderProperties.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}


}
