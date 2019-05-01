package repository;

import model.flatmate.FlatMateEntry;
import model.request.ContactRequest;
import model.request.DeleteFlatRequest;
import model.request.RequestStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeleteFlatRequestRepository extends BaseRequestRepository<DeleteFlatRequest>  {
    List<DeleteFlatRequest> findAllByRequestStatus(RequestStatus requestStatus);
    DeleteFlatRequest findById(String id);
}
