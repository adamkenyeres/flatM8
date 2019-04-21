package service;

import model.request.DeleteMateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteMateRequestService extends AbstractRequestService<DeleteMateRequest> {

    @Autowired
    public DeleteMateRequestService(MongoRepository<DeleteMateRequest, String> repository, UserService userService) {
        super(repository, userService);
    }
}
