package repository;

import model.flat.Address;
import model.flat.Flat;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlatRepository extends MongoRepository<Flat, String> {
    Flat findByAddress(Address address);
    Optional<Flat> findById(String id);
    Flat findByUserEmail(String userEmail);
    void deleteByAddress(Address address);
}
