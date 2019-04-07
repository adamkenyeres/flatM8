package service;

import exception.FlatNotFoundException;
import exception.MultipleFlatForUserException;
import model.flat.Address;
import model.flat.Flat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.FlatRepository;
import util.FlatUtils;

import java.util.List;

@Service
public class FlatService {

    @Autowired
    private FlatRepository flatRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlatService.class);

    public Flat saveFlat(Flat f) throws MultipleFlatForUserException {
        if (f == null || f.getUserEmail() == null) {
            LOGGER.error("Couldn't save flat (user or flat not present).");
            return null;
        }

        Flat flat = flatRepository.findByUserEmail(f.getUserEmail());

        if (flat != null && !flat.equals(f)) {
            LOGGER.error("User {} already has a flat.", f.getUserEmail());
            throw new MultipleFlatForUserException("User already has a flat present.");
        }

        return flatRepository.save(f);
    }
    public Flat getFlatByUserEmail(String email) {
        if (email == null || email.isEmpty()) {
            LOGGER.error("Couldn't find any flats for user, email shouldn't be null or empty.");
            return null;
        }

        Flat f = flatRepository.findByUserEmail(email);

        if (f == null) {
            LOGGER.warn("Couldn't find any flats for user.");
            return null;
        }

        return f;
    }

    public Flat getFlatByAddress(Address address) {
        if (address == null) {
            LOGGER.error("Couldn't find any flats for address, address shouldn't be null.");
            return null;
        }

        Flat f = flatRepository.findByAddress(address);

        if (f == null) {
            LOGGER.warn("Couldn't find any flats for address.");
            return null;
        }

        return f;
    }

    public Flat getFlatByAddressString(String addressString) {

        if (addressString == null) {
            LOGGER.error("String address shouldn't be null.");
            return null;
        }

        String[] addressParts = addressString.split(",");

        if (addressParts.length != 6) {
            LOGGER.error("Address string should be: " +
                    "'city,streetAddress,streetNumber,district,door,floor'. " +
                    "Returning null.");
            return null;
        }

        try {
            Address address = FlatUtils.addressFromStringArray(addressParts);
            return flatRepository.findByAddress(address);
        } catch (Exception e) {
            LOGGER.error("Malformed Address string.");
            return null;
        }
    }

    public List<Flat> getAllFlats() {
        List<Flat> flats = flatRepository.findAll();

        if (flats.isEmpty()) {
            LOGGER.warn("No flats found for getAllFlats call.");
        }
        return flats;
    }

    public void deleteFlat(Flat f) throws FlatNotFoundException {

        if (f == null) {
            LOGGER.error("Flat is null, can't delete.");
            throw new FlatNotFoundException("Flat is null");
        }
        Flat flat = flatRepository.findById(f.getId());

        if (flat == null) {
            LOGGER.warn("Can't find flat by id {}, can't delete.", f.getId());
            throw new FlatNotFoundException("Can't find flat");
        }

        flatRepository.delete(flat);
    }

    public Flat getFlatById(String id) {
        if (id == null || id.isEmpty()) {
            LOGGER.error("id is null or empty, can't fetch flat.");
            return null;
        }

        Flat f = flatRepository.findById(id);
        if (f == null) {
            LOGGER.warn("Can't find flat for id: {}, returning null.", id);
            return null;
        }

        return f;
    }

    public void deleteAll() {
        flatRepository.deleteAll();
    }

    public void deleteFlatByAddress(Address address) throws FlatNotFoundException {
        if (address == null) {
            LOGGER.error("Can't delete flat by address, address shouldn't be null.");
            throw new FlatNotFoundException("Address is null");
        }

        Flat f = flatRepository.findByAddress(address);

        if (f == null) {
            LOGGER.warn("Can't delete flat by address, flat is not found.");
            throw new FlatNotFoundException("Couldn't find flat by address.");
        }

        flatRepository.delete(f);
    }
}
