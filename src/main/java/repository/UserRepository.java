package repository;

import model.tenant.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
    User findByUserName(String userName);
    List<User> findAllByFirstNameAndLastName(String firstName, String lastName);
    Optional<User> findById(String id);
    List<User> getAllByEmailIn(List<String> emails);
}
