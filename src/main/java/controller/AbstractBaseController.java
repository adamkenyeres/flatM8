package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.AbstractBaseService;

import javax.validation.Valid;
import java.util.List;

public abstract class AbstractBaseController<Entity> implements GenericController<Entity> {


    private AbstractBaseService<Entity> service;

    @Autowired
    protected AbstractBaseController(AbstractBaseService<Entity> service) {
        this.service = service;
    }

    @Override
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getAllEntities() {
        List<Entity> entries = service.getAll();

        if (entries.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entries);
        }
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getEntityById(@PathVariable String id) {
        Entity entry = service.getById(id);
        if (entry == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entry);
        }
    }

    @Override
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createEntity(@Valid @RequestBody Entity entry) {
        Entity e = service.createOrUpdate(entry);

        if (e == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(e);
        }
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEntityById(@PathVariable String id) {
        try {
            service.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @RequestMapping(value = "/deleteAllEntities", method = RequestMethod.POST)
    public ResponseEntity deleteAllEntities() {
        try {
            service.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity createResponse(Entity e) {
        if (e == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(e);
        }
    }
}
