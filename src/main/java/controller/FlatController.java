package controller;

import exception.FlatNotFoundException;
import model.flat.Address;
import model.flat.Flat;
import model.tenant.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repository.FlatRepository;
import repository.UserRepository;
import service.FlatService;
import util.FlatUtils;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RequestMapping("/flats")
@RestController
public class FlatController implements GenericController<Flat> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlatController.class);

    private FlatService service;

    @Autowired
    public FlatController(FlatService service, FlatRepository flatRepository) {
        this.service = service;
    }

    @Override
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity getAllEntities() {
        return ResponseEntity.ok(service.getAllFlats());
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getEntityById(@PathVariable String id) {
        Flat f = service.getFlatById(id);
        return f == null ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(f);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity updateEntity(@Valid @RequestBody Flat flat) {
        Flat f = service.updateFlat(flat);

        return f == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(f);
    }

    @Override
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createEntity(@Valid @RequestBody Flat flat) {
        Flat f = null;

        try {
            f = service.saveFlat(flat);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return f == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(f);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEntityById(@PathVariable String id) {
        Flat f = service.getFlatById(id);
        try {
            service.deleteFlat(f);
            return ResponseEntity.ok().build();
        } catch (FlatNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
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

    @RequestMapping(value = "/getFlatByAddressString", method = RequestMethod.GET)
    public ResponseEntity getFlatByAddressString(@RequestParam("address") String addressString) {
        Flat f = service.getFlatByAddressString(addressString);

        if (f == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(f);
        }
    }

    @Override
    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public ResponseEntity deleteAllEntities() {
        try {
            service.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
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
