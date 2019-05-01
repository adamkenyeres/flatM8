package repository;

import model.request.AddMateRequest;
import model.request.RequestStatus;

import java.util.List;

public interface AddMateRequestRepository extends BaseRequestRepository<AddMateRequest> {
    List<AddMateRequest> findAllByRequestStatus(RequestStatus requestStatus);
    AddMateRequest findById(String id);
}
