package controller;

import com.auth0.jwt.JWT;
import model.tenant.User;
import model.tenant.UserLogin;
import model.tenant.UserTokenData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;
import service.SecurityService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.ws.Response;
import java.security.Principal;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static auth.JWTAuthenticationFilter.*;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RestController
@RequestMapping("/")
@CrossOrigin
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
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

    @RequestMapping(value = "/getUserByEmail", method = RequestMethod.GET)
    public ResponseEntity getByEmail(@RequestParam("email") String email) {
        if (!email.contains("@")) {
            LOGGER.error("Email doesn't contain @ sign, returning null.");
            return null;
        }
        User u = repository.findByEmail(email);
        if (u == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(u);
        }
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user) {
        return repository.save(user);
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String email) {
        repository.delete(repository.findByEmail(email));
    }

    @RequestMapping(value = "/user")
    public Principal user(Principal user) {
       return user;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody User userLogin) {
        try {
            return ResponseEntity.ok(userService.register(userLogin));
        } catch (Exception e) {
            LOGGER.error("Error creating user!");
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/loggedInUsername")
    public String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity signIn(@RequestBody UserLogin userLogin) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = JWT.create()
                    .withSubject(((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(HMAC512(SECRET.getBytes()));

            UserTokenData userTokenData = new UserTokenData(userLogin.getEmail(), token);
            return ResponseEntity.ok(userTokenData);
        } catch (Exception ex) {
            LOGGER.error("Error occured while trying to log in user: {}", userLogin.getEmail());
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(this.userService.save(user));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
