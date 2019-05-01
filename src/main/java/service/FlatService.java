package service;

import annotation.ImplicitNullCheck;
import exception.FlatNotFoundException;
import exception.MultipleFlatForUserException;
import model.flat.Address;
import model.flat.Flat;
import model.tenant.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.FlatRepository;
import repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlatService extends AbstractBaseService<Flat> {

    private final FlatRepository flatRepository;

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlatService.class);

    @Autowired
    public FlatService(FlatRepository flatRepository, UserRepository userRepository) {
        super(flatRepository);
        this.flatRepository = flatRepository;
        this.userRepository = userRepository;
    }

    @ImplicitNullCheck
    public Flat updateFlat(Flat f) {
        return flatRepository.save(f);
    }

    @ImplicitNullCheck
    public Flat saveFlat(Flat f) throws MultipleFlatForUserException {
        if (f.getUserEmail() == null) {
            LOGGER.error("Couldn't save flat (user email not present).");
            throw new IllegalArgumentException();
        }

        Flat flat = flatRepository.findByUserEmail(f.getUserEmail());

        if (flat != null && !flat.equals(f)) {
            LOGGER.error("User {} already has a flat.", f.getUserEmail());
            throw new MultipleFlatForUserException("User already has a flat present.");
        }

        return flatRepository.save(f);
    }

    @ImplicitNullCheck
    public Flat updateFlatWithUser(User user) {
        Flat flat = getFlatForFlatMate(user.getEmail());

        if (flat == null || flat.getFlatMates() == null) {
            return null;
        }

        flat.getFlatMates().remove(user);
        flat.getFlatMates().add(user);

        return updateFlat(flat);
    }

    @ImplicitNullCheck
    public Flat getFlatForFlatMate(String email) {
        List<Flat> flats = flatRepository.findAll();

        User u = userRepository.findByEmail(email);

        if (u == null) {
            LOGGER.error("Can't find user with email: {}", email);
            return null;
        }

        List<Flat> userInFlats = flats.stream()
                .filter(f -> f.getFlatMates().contains(u) || f.getUserEmail().equals(email))
                .collect(Collectors.toList());

        if (userInFlats.size() == 1) {
            return userInFlats.get(0);
        } else if (userInFlats.size() == 0) {
            return null;
        } else {
            LOGGER.error("Invalid state: User with email: {} is present as flatmate in multiple flats.", email);
            return null;
        }
    }

    @ImplicitNullCheck
    public Flat getFlatByAddress(Address address) {
        Flat f = flatRepository.findByAddress(address);

        if (f == null) {
            LOGGER.warn("Couldn't find any flats for address.");
            return null;
        }

        return f;
    }

    @ImplicitNullCheck
    public void deleteFlatByAddress(Address address) throws FlatNotFoundException {
        flatRepository.deleteByAddress(address);
    }
}
