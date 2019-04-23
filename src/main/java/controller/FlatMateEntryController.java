package controller;

import model.criteria.BaseCriteria;
import model.criteria.LifestyleCriteria;
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
import service.AbstractBaseService;
import service.FlatMateEntryService;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flatmateEntries")
public class FlatMateEntryController extends AbstractBaseController<FlatMateEntry> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlatMateEntryController.class);

    private final FlatMateEntryService flatMateEntryService;

    @Autowired
    public FlatMateEntryController(FlatMateEntryService flatMateEntryService) {
        super(flatMateEntryService);
        this.flatMateEntryService = flatMateEntryService;
    }


    @RequestMapping(value = "/deleteEntry", method = RequestMethod.POST)
    public ResponseEntity deleteEntry(@Valid @RequestBody FlatMateEntry entry) {
        try {
            flatMateEntryService.deleteEntry(entry);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/getAllForFlat", method = RequestMethod.POST)
    public ResponseEntity getAllEntriesForFlat(@RequestBody Flat flat) {

        List<FlatMateEntry> entries = flatMateEntryService.getAllEntriesForFlat(flat);

        if (entries.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entries);
        }
    }

    @RequestMapping(value = "/deleteAllForFlat", method = RequestMethod.POST)
    public ResponseEntity deleteAllForFlat(@RequestBody Flat flat) {
        List<FlatMateEntry> entries = flatMateEntryService.deleteAllForFlat(flat);
        if (entries.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @RequestMapping(value = "/getEntriesForAge", method = RequestMethod.GET)
    public ResponseEntity getEntriesForAge(@RequestParam Integer age) {
        List<FlatMateEntry> entries = flatMateEntryService.getByAgeRadius(age);
        if (entries.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entries);
        }
    }

    @RequestMapping(value = "/getEntriesForLifeStyle", method = RequestMethod.GET)
    public ResponseEntity getEntriesForLifestyle(@RequestParam String lifestyleCriteria) {
        List<FlatMateEntry> entries = flatMateEntryService.getByLifeStyle(lifestyleCriteria);
        if (entries.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entries);
        }
    }

    @RequestMapping(value = "/getUltimateEntries", method = RequestMethod.GET)
    public ResponseEntity getUltimateEntries(@RequestParam String lifestyleCriteria, @RequestParam Integer age) {
        List<FlatMateEntry> entries = flatMateEntryService.getUltimateMatches(lifestyleCriteria, age);
        if (entries.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(entries);
        }
    }
}
