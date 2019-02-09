package main;

import controller.FlatController;
import controller.FlatMateEntryController;
import controller.UserController;
import model.flat.Flat;
import model.flat.FlatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import repository.UserRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "repository")
@ComponentScan(basePackages = "controller")
public class Main implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
    }


}
