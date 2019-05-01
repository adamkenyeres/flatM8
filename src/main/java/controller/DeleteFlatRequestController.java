package controller;

import model.request.DeleteFlatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.DeleteFlatRequestService;

@RequestMapping("/deleteFlatRequests")
@RestController
public class DeleteFlatRequestController extends AbstractRequestController<DeleteFlatRequest>  {

    private final DeleteFlatRequestService deleteFlatRequestService;

    @Autowired
    public DeleteFlatRequestController(DeleteFlatRequestService deleteFlatRequestService) {
        super(deleteFlatRequestService);
        this.deleteFlatRequestService = deleteFlatRequestService;
    }
}
