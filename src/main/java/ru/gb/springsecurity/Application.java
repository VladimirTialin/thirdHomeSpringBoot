package ru.gb.springsecurity;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gb.springsecurity.model.Reader;
import ru.gb.springsecurity.model.ReaderProperties;
import ru.gb.springsecurity.model.Role;
import ru.gb.springsecurity.model.User;
import ru.gb.springsecurity.repository.UserRepository;


@SpringBootApplication
@EnableConfigurationProperties(ReaderProperties.class)
@NoArgsConstructor
public class Application {
	public static void main(String[] args) {
//		ConfigurableApplicationContext context =
				SpringApplication.run(Application.class, args);
//		UserRepository userRepository = context.getBean(UserRepository.class);
//		PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
//		saveUser(userRepository, "admin", passwordEncoder);
//		saveUser(userRepository, "reader", passwordEncoder);

	}
	private static void saveUser(UserRepository repository, String login, PasswordEncoder passwordEncoder) {
		User readerUser = new User();
		Role role = new Role();
		Reader reader = new Reader();
		reader.setName(login);
		readerUser.setLogin(login);
		readerUser.setPass(passwordEncoder.encode(login));
		readerUser.setReader(reader);
		role.setName(login);
		readerUser.addRole(role);
		repository.save(readerUser);
	}

}
