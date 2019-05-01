package service;

import model.request.AddMateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AddMateRequestRepository;

@Service
public class AddMateRequestService extends AbstractRequestService<AddMateRequest> {

    @Autowired
    public AddMateRequestService(AddMateRequestRepository addMateRequestRepository, UserService userService) {
        super(userService,addMateRequestRepository);
    }
}
