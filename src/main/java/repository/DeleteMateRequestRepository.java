package repository;

import model.request.ContactRequest;
import model.request.DeleteMateRequest;
import model.request.RequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeleteMateRequestRepository extends MongoRepository<DeleteMateRequest, String> {

    List<DeleteMateRequest> findAllByRequestStatus(RequestStatus requestStatus);
    DeleteMateRequest findById(String id);
}
