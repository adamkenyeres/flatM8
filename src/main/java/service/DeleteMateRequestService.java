package service;

import model.request.DeleteMateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.DeleteMateRequestRepository;

@Service
public class DeleteMateRequestService extends AbstractRequestService<DeleteMateRequest> {

    @Autowired
    public DeleteMateRequestService(DeleteMateRequestRepository repository, UserService userService) {
        super(userService,repository);
    }
}
