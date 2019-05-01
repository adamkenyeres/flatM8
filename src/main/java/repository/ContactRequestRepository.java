package repository;

import model.request.AddMateRequest;
import model.request.ContactRequest;
import model.request.RequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContactRequestRepository extends BaseRequestRepository<ContactRequest> {
    List<ContactRequest> findAllByRequestStatus(RequestStatus requestStatus);
    ContactRequest findById(String id);
}
