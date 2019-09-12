package repository;

import model.request.ContactRequest;
import model.request.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface ContactRequestRepository extends BaseRequestRepository<ContactRequest> {
    List<ContactRequest> findAllByRequestStatus(RequestStatus requestStatus);
    Optional<ContactRequest> findById(String id);
}
