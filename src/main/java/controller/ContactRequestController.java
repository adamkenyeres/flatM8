package controller;

import model.request.ContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ContactRequestService;

@RequestMapping("/contactRequests")
@RestController
public class ContactRequestController extends AbstractRequestController<ContactRequest> {

    private final ContactRequestService contactRequestService;

    @Autowired
    public ContactRequestController(ContactRequestService contactRequestService) {
        super(contactRequestService);
        this.contactRequestService = contactRequestService;
    }
}
