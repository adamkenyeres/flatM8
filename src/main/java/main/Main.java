package main;

import controller.FlatController;
import controller.FlatMateEntryController;
import controller.UserController;
import model.flat.Flat;
import model.flat.FlatType;
import model.tenant.Role;
import model.tenant.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import repository.RoleRepository;
import repository.UserRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "repository")
@ComponentScan(basePackages = {"controller", "service", "config"})
@EnableAutoConfiguration
public class Main implements CommandLineRunner {

    @Autowired
    private UserController userController;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        repository.deleteAll();
        roleRepository.deleteAll();
        Role r = new Role();
        r.setName("USER");
        roleRepository.save(r);
    }


}
