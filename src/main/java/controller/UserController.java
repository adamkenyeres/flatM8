package controller;

import model.tenant.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @RequestMapping(value = "/getByName", method = RequestMethod.GET)
    public List<User> getUsersByName(@RequestParam("name") String name) {
        String[] nameParts = name.split(" ");
        if (nameParts.length != 2) {
            LOGGER.error("Name has more than 2 parts, returning empty collection.");
            return Collections.emptyList();
        }
        return repository.findAllByFirstNameAndLastName(nameParts[0], nameParts[1]);
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public User getByEmail(@PathVariable("email") String email) {
        if (!email.contains("@")) {
            LOGGER.error("Email doesn't contain @ sign, returning null.");
            return null;
        }
        return repository.findByEmail(email);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user) {
        return repository.save(user);
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String email) {
        repository.delete(repository.findByEmail(email));
    }
}
