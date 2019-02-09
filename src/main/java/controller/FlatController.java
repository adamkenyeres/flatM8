package controller;

import model.flat.Address;
import model.flat.Flat;
import model.tenant.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repository.FlatRepository;
import repository.UserRepository;
import util.FlatUtils;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RequestMapping("/flats")
@RestController
public class FlatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlatController.class);
    private FlatRepository repository;

    @Autowired
    public FlatController(FlatRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Flat> getAllFlats() {
        return repository.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Flat createFlat(@Valid @RequestBody Flat flat) {
        return repository.save(flat);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteFlatById(@PathVariable String id) {
        repository.delete(repository.findById(id));
    }

    @RequestMapping(value = "/deleteByAddress", method = RequestMethod.DELETE)
    public void deleteFlatByAddress(@Valid @RequestBody Address address) {
        repository.delete(repository.findByAddress(address));
    }

    @RequestMapping(value = "/getFlatByAddress", method = RequestMethod.GET)
    public Flat getFlatByAddress(@RequestBody Address address) {
        return repository.findByAddress(address);
    }

    @RequestMapping(value = "/getFlatByAddressString", method = RequestMethod.GET)
    public Flat getFlatByAddressString(@RequestParam("address") String addressString) {
        String[] addressParts = addressString.split(",");

        if (addressParts.length != 6) {
            LOGGER.error("Address string should be: " +
                    "'city,streetAddress,streetNumber,district,door,floor'. " +
                    "Returning null.");
            return null;
        }

        Address address = FlatUtils.addressFromStringArray(addressParts);
        return repository.findByAddress(address);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public void deleteAll() {
        repository.deleteAll();
    }
}
