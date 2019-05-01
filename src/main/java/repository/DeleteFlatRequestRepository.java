package repository;

import model.request.DeleteFlatRequest;
import model.request.RequestStatus;

import java.util.List;

public interface DeleteFlatRequestRepository extends BaseRequestRepository<DeleteFlatRequest>  {
    List<DeleteFlatRequest> findAllByRequestStatus(RequestStatus requestStatus);
    DeleteFlatRequest findById(String id);
}
