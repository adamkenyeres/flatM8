package service;

import model.request.ContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import repository.ContactRequestRepository;

@Service
public class ContactRequestService extends AbstractRequestService<ContactRequest> {

    @Autowired
    public ContactRequestService(ContactRequestRepository repository, UserService userService) {
        super(userService, repository);
    }
}
