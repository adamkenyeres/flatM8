package repository;

import model.flat.Address;
import model.flat.Flat;
import model.tenant.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlatRepository extends MongoRepository<Flat, String> {

    Flat findByAddress(Address address);
    Flat findById(String id);
}
