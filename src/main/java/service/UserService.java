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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User save(User userLogin) throws Exception {

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

    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

}
