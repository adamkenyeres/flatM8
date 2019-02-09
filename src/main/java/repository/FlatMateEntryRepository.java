package repository;

import model.flat.Flat;
import model.flatmate.FlatMateEntry;
import model.tenant.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FlatMateEntryRepository extends MongoRepository<FlatMateEntry, String> {

    List<FlatMateEntry> findAllByMainTenant(User mainTenant);
    List<FlatMateEntry> findAllByRemainingFreeSpaceCount(Integer freeSpace);
    FlatMateEntry findById(String id);
}
