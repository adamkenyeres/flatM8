package service;

import annotation.ImplicitNullCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public abstract class AbstractBaseService<Entity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseService.class);
    protected MongoRepository<Entity, String> repository;

    @Autowired
    protected AbstractBaseService(MongoRepository<Entity, String> repository) {
        this.repository = repository;
    }

    @ImplicitNullCheck
    public void delete(Entity entry) {
        repository.delete(entry);
    }

    public List<Entity> getAll() {
        return repository.findAll();
    }

    @ImplicitNullCheck
    public Entity getById(String id) {
        return repository.findOne(id);
    }

    @ImplicitNullCheck
    public Entity createOrUpdate(Entity entry) {
        return repository.save(entry);
    }

    @ImplicitNullCheck
    public void deleteById(String id) throws IllegalArgumentException {
        repository.delete(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
