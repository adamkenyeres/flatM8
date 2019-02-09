package repository;

import model.flatmate.FlatMateEntry;
import model.request.ContactRequest;
import model.tenant.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContactRequestRepository extends MongoRepository<ContactRequest, String> {

    List<ContactRequest> findAllBySender(User sender);
    List<ContactRequest> findAllByEntry(FlatMateEntry entry);
}
