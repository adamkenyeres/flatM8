package repository;

import model.request.AddMateRequest;
import model.request.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface AddMateRequestRepository extends BaseRequestRepository<AddMateRequest> {
    List<AddMateRequest> findAllByRequestStatus(RequestStatus requestStatus);
    Optional<AddMateRequest> findById(String id);
}
