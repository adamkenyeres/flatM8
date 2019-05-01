package service;

import annotation.ImplicitNullCheck;
import model.tenant.Role;
import model.tenant.User;
import model.tenant.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.RoleRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService extends AbstractBaseService<User> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @ImplicitNullCheck
    public User register(User userLogin) throws Exception {
        User u = userRepository.findByEmail(userLogin.getEmail());
        List<Role> roles = new ArrayList<>();

        if (u != null) {
            throw new Exception("User with this email is already registered!");
        }

        for (Role r : userLogin.getRoles()) {
            Role tmp = roleRepository.getByName(r.getName());
            roles.add(tmp);
        }

        userLogin.setPassword(bCryptPasswordEncoder.encode(userLogin.getPassword()));
        userLogin.setRoles(new HashSet<>(roles));
        return userRepository.save(userLogin);
    }

    @ImplicitNullCheck
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @ImplicitNullCheck
    public List<User> getUsersByEmail(List<String> emails) {
        return userRepository.getAllByEmailIn(emails);
    }

    public String getLoggedInEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }
}
