package service;

import model.request.DeleteFlatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.DeleteFlatRequestRepository;

@Service
public class DeleteFlatRequestService extends AbstractRequestService<DeleteFlatRequest> {

    @Autowired
    public DeleteFlatRequestService(DeleteFlatRequestRepository repository, UserService userService) {
        super(userService, repository);
    }
}
