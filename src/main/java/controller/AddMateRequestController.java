package controller;

import model.request.AddMateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.AddMateRequestService;
import service.ContactRequestService;

@RequestMapping("/addMateRequests")
@RestController
public class AddMateRequestController extends AbstractRequestController<AddMateRequest> {

    private final AddMateRequestService addMateRequestService;

    @Autowired
    public AddMateRequestController(AddMateRequestService addMateRequestService) {
        super(addMateRequestService);
        this.addMateRequestService = addMateRequestService;
    }
}
