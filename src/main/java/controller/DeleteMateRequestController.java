package controller;

import model.request.DeleteMateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.DeleteMateRequestService;

@RequestMapping("/deleteMateRequests")
@RestController
public class DeleteMateRequestController extends AbstractRequestController<DeleteMateRequest> {

    private final DeleteMateRequestService deleteMateRequestService;

    @Autowired
    public DeleteMateRequestController(DeleteMateRequestService deleteMateRequestService) {
        super(deleteMateRequestService);
        this.deleteMateRequestService = deleteMateRequestService;
    }
}
