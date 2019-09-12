package repository;

import model.request.DeleteMateRequest;
import model.request.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface DeleteMateRequestRepository extends BaseRequestRepository<DeleteMateRequest> {
    List<DeleteMateRequest> findAllByRequestStatus(RequestStatus requestStatus);
    Optional<DeleteMateRequest> findById(String id);
}
