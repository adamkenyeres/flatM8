package repository;

import model.request.DeleteMateRequest;
import model.request.RequestStatus;

import java.util.List;

public interface DeleteMateRequestRepository extends BaseRequestRepository<DeleteMateRequest> {
    List<DeleteMateRequest> findAllByRequestStatus(RequestStatus requestStatus);
    DeleteMateRequest findById(String id);
}
