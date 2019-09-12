package repository;

import model.request.DeleteFlatRequest;
import model.request.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface DeleteFlatRequestRepository extends BaseRequestRepository<DeleteFlatRequest>  {
    List<DeleteFlatRequest> findAllByRequestStatus(RequestStatus requestStatus);
    Optional<DeleteFlatRequest> findById(String id);
}
