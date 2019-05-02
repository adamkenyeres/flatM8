package controller;

import model.request.BaseRequest;
import model.tenant.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AbstractRequestService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

public abstract class AbstractRequestController<T extends BaseRequest> extends AbstractBaseController<T> {

    private AbstractRequestService<T> abstractRequestService;

    @Autowired
    public AbstractRequestController(AbstractRequestService<T> abstractRequestService) {
        super(abstractRequestService);
        this.abstractRequestService = abstractRequestService;
    }

    @Override
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity getAllEntities() {
        List<T> requests = abstractRequestService.getAll();
        if (isEmpty(requests)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(requests);
        }
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getEntityById(@PathVariable String id) {
        T entity = abstractRequestService.getById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entity);
        }
    }

    @Override
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createEntity(@Valid @RequestBody T request) {
        T entity = abstractRequestService.createRequestWithDuplicateCheck(request);
        if (entity == null || entity.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entity);
        }
    }

    @RequestMapping(value = "/updateRequest", method = RequestMethod.POST)
    public ResponseEntity updateEntity(@Valid @RequestBody T request) {
        T entity = abstractRequestService.createOrUpdate(request);

        if (entity == null || entity.getId() == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entity);
        }
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEntityById(@PathVariable String id) {
        T addMateRequest = abstractRequestService.getById(id);

        if (addMateRequest == null) {
            return ResponseEntity.notFound().build();
        } else {
            abstractRequestService.delete(addMateRequest);
            return ResponseEntity.ok().build();
        }
    }

    @Override
    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public ResponseEntity deleteAllEntities() {
        try {
            abstractRequestService.deleteAll();
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

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity updateUser(@RequestBody User user) {
        List<T> updated = abstractRequestService.updateUsersInRequests(user);
        if (isEmpty(updated)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
