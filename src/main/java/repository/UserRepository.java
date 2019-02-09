package repository;

import model.tenant.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
    List<User> findAllByFirstNameAndLastName(String firstName, String lastName);
    User findById(String id);
}
