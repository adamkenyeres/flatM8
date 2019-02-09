package controller;

import model.criteria.BaseCriteria;
import model.flatmate.FlatMateEntry;
import model.tenant.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FlatMateEntryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlatMateEntryController.class);

    private final FlatMateEntryRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public FlatMateEntryController(FlatMateEntryRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<FlatMateEntry> getAllUsers() {
        return repository.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public FlatMateEntry createFlatMateEntry(@Valid @RequestBody FlatMateEntry flat) {
        return repository.save(flat);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteFlatMateEntryById(@PathVariable String id) {
        repository.delete(repository.findById(id));
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
    public List<FlatMateEntry> getAllEntriesForMainTenant(@PathVariable String tenantEmail) {
        User mainTenant = userRepository.findByEmail(tenantEmail);
        if (mainTenant == null) {
            LOGGER.warn("Couldn't find main tenant with email: {}", tenantEmail);
            return Collections.emptyList();
        }
        return repository.findAllByMainTenant(mainTenant);
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
