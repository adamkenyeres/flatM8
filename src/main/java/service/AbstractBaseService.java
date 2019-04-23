package service;

import model.flat.Flat;
import model.flatmate.FlatMateEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractBaseService<Entity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseService.class);

    protected MongoRepository<Entity, String> repository;

    @Autowired
    protected AbstractBaseService(MongoRepository<Entity, String> repository) {
        this.repository = repository;
    }

    public void deleteEntry(Entity flatMateEntry) throws IllegalArgumentException {
        repository.delete(flatMateEntry);
    }

    public List<Entity> getAllEntries() {
        return repository.findAll();
    }

    public Entity getEntryById(String id) {
        return repository.findOne(id);
    }

    public Entity createEntry(Entity entry) {
        return repository.save(entry);
    }

    public void deleteById(String id) throws IllegalArgumentException {
        repository.delete(id);
    }

    public void deleteAllEntries() {
        repository.deleteAll();
    }
}
