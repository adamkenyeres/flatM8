package repository;

import model.flat.Flat;
import model.flatmate.FlatMateEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FlatMateEntryRepository extends MongoRepository<FlatMateEntry, String> {
    Optional<FlatMateEntry> findById(String id);
    List<FlatMateEntry> findAllByFlat(Flat flat);
    void deleteAllByFlat(Flat flat);
}
