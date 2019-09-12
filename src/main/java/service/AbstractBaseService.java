package service;

import annotation.ImplicitNullCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

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
        return repository.findById(id)
        		.orElse(null);
    }

    @ImplicitNullCheck
    public Entity createOrUpdate(Entity entry) {
        return repository.save(entry);
    }

    @ImplicitNullCheck
    public void deleteById(String addMateRequestId) throws IllegalArgumentException {
        repository.deleteById(addMateRequestId);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
