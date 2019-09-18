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
import org.springframework.web.bind.annotation.*;
import service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import static auth.JWTAuthenticationFilter.EXPIRATION_TIME;
import static auth.JWTAuthenticationFilter.SECRET;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RestController
@RequestMapping("/")
@CrossOrigin
public class UserController extends AbstractBaseController<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        super(userService);
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @RequestMapping(value = "/getUserByEmail", method = RequestMethod.GET)
    public ResponseEntity getByEmail(@RequestParam("email") String email) {
        User u = userService.getUserByEmail(email);
        return createResponse(u);
    }

    @RequestMapping(value = "/getUserByUserName", method = RequestMethod.GET)
    public ResponseEntity getByUserName(@RequestParam("userName") String userName) {
        User u = userService.getUserByUserName(userName);
        return createResponse(u);
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user) {
        return userService.createOrUpdate(user);
    }

    @RequestMapping(value = "/deleteByEmail/{email}", method = RequestMethod.DELETE)
    public void deleteUserByEmail(@PathVariable String email) {
        userService.delete(userService.getUserByEmail(email));
    }

    @RequestMapping(value = "/deleteByUserName/{userName}", method = RequestMethod.DELETE)
    public void deleteUserByUserName(@PathVariable String userName) {
        userService.delete(userService.getUserByUserName(userName));
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public void deleteAllUsers() {
        userService.deleteAll();
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
    public ResponseEntity getLoggedInUsername() {
        String email = userService.getLoggedInEmail();
        return email == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(email);
    }

    @RequestMapping(value = "/signinByEmail", method = RequestMethod.POST)
    public ResponseEntity signInByEmail(@RequestBody UserLogin userLogin) {
        return signIn(userLogin.getLoginName(), userLogin.getPassword());
    }

    @RequestMapping(value = "/signinByUserName", method = RequestMethod.POST)
    public ResponseEntity signInByUserName(@RequestBody UserLogin userLogin) {
        try {
            User u = userService.getUserByUserName(userLogin.getLoginName());
            String email = u.getEmail();
            return signIn(email, userLogin.getPassword());
        } catch (Exception e) {
            LOGGER.error("Error occured while trying to log in user (user not found): {}", userLogin.getLoginName());
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity signIn(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = JWT.create()
                    .withSubject(((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(HMAC512(SECRET.getBytes()));

            UserTokenData userTokenData = new UserTokenData(email, token);
            return ResponseEntity.ok(userTokenData);
        } catch (Exception ex) {
            LOGGER.error("Error occured while trying to log in user: {}", email);
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(this.userService.createOrUpdate(user));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
