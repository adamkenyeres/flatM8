package repository;

import model.request.AddMateRequest;
import model.request.RequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AddMateRequestRepository extends BaseRequestRepository<AddMateRequest> {
    List<AddMateRequest> findAllByRequestStatus(RequestStatus requestStatus);
    AddMateRequest findById(String id);
}
