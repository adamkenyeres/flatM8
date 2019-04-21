package service;

import model.tenant.Role;
import model.tenant.User;
import model.tenant.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import repository.RoleRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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
    public User save(User userLogin) {
        return userRepository.save(userLogin);
    }

    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
    }
}
