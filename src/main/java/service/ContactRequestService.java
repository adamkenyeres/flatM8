package service;

import model.request.ContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactRequestService extends AbstractRequestService<ContactRequest> {

    @Autowired
    public ContactRequestService(MongoRepository<ContactRequest, String> repository, UserService userService) {
        super(repository, userService);
    }
}
