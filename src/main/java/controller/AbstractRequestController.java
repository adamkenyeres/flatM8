package controller;

import model.request.AddMateRequest;
import model.request.BaseRequest;
import model.request.RequestStatus;
import model.tenant.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AbstractRequestService;
import service.AddMateRequestService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

public abstract class AbstractRequestController<T extends BaseRequest> implements GenericController<T> {

    private AbstractRequestService<T> abstractRequestService;

    @Autowired
    public AbstractRequestController(AbstractRequestService<T> abstractRequestService) {
        this.abstractRequestService = abstractRequestService;
    }

    @Override
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity getAllEntities() {
        List<T> requests = abstractRequestService.getRequests();
        if (isEmpty(requests)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(requests);
        }
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getEntityById(@PathVariable String id) {
        T entity = getById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entity);
        }
    }

    @Override
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createEntity(@Valid @RequestBody T request) {
        List<T> reqs = abstractRequestService.getRequests();
        boolean existing = abstractRequestService.getRequests()
                .stream()
                .anyMatch(r -> r.equals(request)
                        && r.getRequestStatus().equals(RequestStatus.PENDING));

        if (existing) {
            return ResponseEntity.badRequest().build();
        }
        T entity = abstractRequestService.saveRequest(request);

        if (entity.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entity);
        }
    }

    @RequestMapping(value = "/updateRequest", method = RequestMethod.POST)
    public ResponseEntity updateEntity(@Valid @RequestBody T request) {
        boolean existing = abstractRequestService.getRequests()
                .stream()
                .anyMatch(r -> r.equals(request)
                        && RequestStatus.PENDING.equals(r.getRequestStatus()));

        if (!existing) {
            return ResponseEntity.notFound().build();
        }

        T entity = abstractRequestService.saveRequest(request);

        if (entity.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entity);
        }
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEntityById(@PathVariable String id) {
        T addMateRequest = getById(id);

        if (addMateRequest == null) {
            return ResponseEntity.notFound().build();
        } else {
            abstractRequestService.deleteRequest(addMateRequest);
            return ResponseEntity.ok().build();
        }
    }

    @Override
    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public ResponseEntity deleteAllEntities() {
        try {
            abstractRequestService.deleteRequests();
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/getForUser", method = RequestMethod.GET)
    public ResponseEntity getForUser(@RequestParam String email) {
        List<T> requests = abstractRequestService.getRequestsForUsers(email);

        if (isEmpty(requests)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(requests);
        }
    }

    @RequestMapping(value = "/getMyRequests", method = RequestMethod.GET)
    public ResponseEntity getMyRequests(@RequestParam String email) {
        List<T> requests = abstractRequestService.getMyRequests(email);

        if (isEmpty(requests)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(requests);
        }
    }

    private T getById(String id) {
        List<T> requests = abstractRequestService.getRequests()
                .stream()
                .filter(r -> r.getId().equals(id))
                .collect(Collectors.toList());

        if (isEmpty(requests) || requests.size() != 1) {
            return null;
        } else {
            return requests.get(0);
        }
    }
}
