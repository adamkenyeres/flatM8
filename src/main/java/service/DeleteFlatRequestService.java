package service;

import model.request.ContactRequest;
import model.request.DeleteFlatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteFlatRequestService extends AbstractRequestService<DeleteFlatRequest> {

    @Autowired
    public DeleteFlatRequestService(MongoRepository<DeleteFlatRequest, String> repository, UserService userService) {
        super(repository, userService);
    }
}
