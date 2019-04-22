package controller;

import model.criteria.BaseCriteria;
import model.flat.Flat;
import model.flatmate.FlatMateEntry;
import model.tenant.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repository.FlatMateEntryRepository;
import repository.UserRepository;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flatmateEntries")
public class FlatMateEntryController implements GenericController<FlatMateEntry> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlatMateEntryController.class);

    private final FlatMateEntryRepository repository;

    @Autowired
    public FlatMateEntryController(FlatMateEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getAllEntities() {
        try {
            return ResponseEntity.ok(repository.findAll());
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity getEntityById(String id) {
        try {
            return ResponseEntity.ok(repository.findById(id));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createEntity(@Valid @RequestBody FlatMateEntry flat) {
        try {
            return ResponseEntity.ok(repository.save(flat));
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEntityById(@PathVariable String id) {
        try {
            repository.delete(repository.findById(id));
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity deleteAllEntities() {
        try {
            repository.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/getAllForFlat", method = RequestMethod.POST)
    public ResponseEntity getAllEntriesForFlat(@RequestBody Flat flat) {
        List<FlatMateEntry> entries = repository.findAll()
                .stream()
                .filter(e -> flat.equals(e.getFlat()))
                .collect(Collectors.toList());

        if (entries.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entries);
        }
    }
}
