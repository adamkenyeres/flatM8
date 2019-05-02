package controller;

import exception.FlatNotFoundException;
import model.flat.Address;
import model.flat.Flat;
import model.tenant.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.FlatService;

import javax.validation.Valid;

@RequestMapping("/flats")
@RestController
public class FlatController extends AbstractBaseController<Flat> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlatController.class);

    private FlatService service;

    @Autowired
    public FlatController(FlatService service) {
        super(service);
        this.service = service;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity updateEntity(@Valid @RequestBody Flat flat) {
        Flat f = service.updateFlat(flat);

        return f == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(f);
    }

    @RequestMapping(value = "/deleteByAddress", method = RequestMethod.DELETE)
    public ResponseEntity deleteFlatByAddress(@Valid @RequestBody Address address) {
        try {
            service.deleteFlatByAddress(address);
            return ResponseEntity.ok().build();
        } catch (FlatNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/getFlatByAddress", method = RequestMethod.GET)
    public ResponseEntity getFlatByAddress(@RequestBody Address address) {
        Flat f = service.getFlatByAddress(address);
        return f == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(f);
    }

    @RequestMapping(value = "/getForUser", method = RequestMethod.GET)
    public ResponseEntity getFlatForUser(@RequestParam("email") String email) {
        Flat f = service.getFlatForFlatMate(email);
        if (f == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(f);
        }
    }

    @RequestMapping(value = "/updateUserInFlat", method = RequestMethod.POST)
    public ResponseEntity updateUserInFlat(@RequestBody User user) {
        Flat f = service.updateFlatWithUser(user);
        if (f == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(f);
        }
    }
}
