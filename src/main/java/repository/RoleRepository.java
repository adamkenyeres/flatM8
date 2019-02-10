package repository;

import model.tenant.Role;
import model.tenant.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role getByName(String name);
}
