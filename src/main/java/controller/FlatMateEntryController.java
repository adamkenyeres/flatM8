package controller;

import model.chat.ChatMessage;
import model.flat.Flat;
import model.flatmate.FlatMateEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ChatMessageService;
import service.FlatMateEntryService;
import service.UserService;

import javax.validation.Valid;
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
            flatMateEntryService.deleteEntryWithConversations(entry);
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
       try {
           flatMateEntryService.deleteAllForFlat(flat);
           return ResponseEntity.ok().build();
       } catch (Exception e) {
           return ResponseEntity.notFound().build();
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
