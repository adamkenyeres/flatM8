package controller;

import model.criteria.BaseCriteria;
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
    private final UserRepository userRepository;

    @Autowired
    public FlatMateEntryController(FlatMateEntryRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
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

    @RequestMapping(value = "/deleteAllForMainTenant/{email}", method = RequestMethod.DELETE)
    public void deleteAllForMainTenant(@PathVariable String email) {
        User mainTenant = userRepository.findByEmail(email);
        if (mainTenant == null) {
            LOGGER.warn("Couldn't find main tenant with email: {}", email);
            return;
        }
        List<FlatMateEntry> flatMateEntries = repository.findAllByMainTenant(mainTenant);
        repository.delete(flatMateEntries);
    }

    @RequestMapping(value = "/getAllForCriteria", method = RequestMethod.GET)
    public List<FlatMateEntry> getAllEntriesForCriteria(@Valid @RequestBody BaseCriteria criteria) {
        List<FlatMateEntry> flatMateEntries = repository.findAll();

        return flatMateEntries
                .stream()
                .filter(entry -> entry.getRoomCriterias()
                        .stream()
                        .anyMatch(roomCriteria -> roomCriteria.getCriteria().equals(criteria))
                )
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/getAllForMainTenant/{tenantEmail}", method = RequestMethod.GET)
    public ResponseEntity getAllEntriesForMainTenant(@PathVariable String tenantEmail) {
        User mainTenant = userRepository.findByEmail(tenantEmail);
        if (mainTenant == null) {
            LOGGER.warn("Couldn't find main tenant with email: {}", tenantEmail);
            return ResponseEntity.badRequest().build();
        }
        List<FlatMateEntry> entries = repository.findAllByMainTenant(mainTenant);

        if (entries.isEmpty()) {
            LOGGER.warn("Couldn't find any entries for user: {}", tenantEmail);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entries);
    }

    @RequestMapping(value = "/getAllForTenant/{tenantEmail}", method = RequestMethod.GET)
    public List<FlatMateEntry> getAllEntriesForTenant(@PathVariable String tenantEmail) {
        List<FlatMateEntry> all = repository.findAll();
        User tenant = userRepository.findByEmail(tenantEmail);

        return all
                .stream()
                .filter(e -> e.getTenants().stream().anyMatch(t -> t.equals(tenant)))
                .collect(Collectors.toList());
    }


}
