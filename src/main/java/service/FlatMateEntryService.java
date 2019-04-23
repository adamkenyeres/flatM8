package service;

import model.criteria.LifestyleCriteria;
import model.flat.Flat;
import model.flatmate.FlatMateEntry;
import model.tenant.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.FlatMateEntryRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlatMateEntryService extends AbstractBaseService<FlatMateEntry> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlatMateEntryService.class);
    private final FlatMateEntryRepository repository;

    @Autowired
    public FlatMateEntryService(FlatMateEntryRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<FlatMateEntry> getAllEntriesForFlat(Flat flat) {
        if (flat == null) {
            LOGGER.warn("getAllEntriesForFlat received null flat");
            return Collections.emptyList();
        }

        return repository.findAll()
                .stream()
                .filter(e -> flat.equals(e.getFlat()))
                .collect(Collectors.toList());
    }

    public List<FlatMateEntry> deleteAllForFlat(Flat flat) {
        List<FlatMateEntry> entries = repository.findAll()
                .stream()
                .filter(e -> flat.equals(e.getFlat()))
                .collect(Collectors.toList());

        repository.delete(entries);

        return entries;
    }

    public List<FlatMateEntry> getByAgeRadius(Integer age) {
        return repository.findAll()
                .stream()
                .filter(e ->
                        (e.getRoomCriteria().getCriteria().getAgeCriteria() - e.getRoomCriteria().getCriteria().getAgeOffset()) <= age
                        && (e.getRoomCriteria().getCriteria().getAgeCriteria() + e.getRoomCriteria().getCriteria().getAgeOffset()) >= age)
                .collect(Collectors.toList());
    }

    public List<FlatMateEntry> getByLifeStyle(String lifestyleCriteria) {
        if (lifestyleCriteria == null) {
            return Collections.emptyList();
        }

        LifestyleCriteria crit = LifestyleCriteria.valueOf(lifestyleCriteria);

        return repository.findAll()
                .stream()
                .filter(e -> e.getRoomCriteria().getCriteria().getLifestyleCriteria().equals(crit))
                .collect(Collectors.toList());
    }

    public List<FlatMateEntry> getUltimateMatches(String lifestyleCriteria, Integer age) {
        List<FlatMateEntry> entries = getByAgeRadius(age);
        entries.retainAll(getByLifeStyle(lifestyleCriteria));

        return entries;
    }
}
