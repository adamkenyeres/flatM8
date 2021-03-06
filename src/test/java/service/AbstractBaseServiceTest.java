package service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.mongodb.repository.MongoRepository;

public abstract class AbstractBaseServiceTest<Entity> {

    @Mock
    protected MongoRepository<Entity, String> repository;

    protected AbstractBaseService<Entity> service;

    @Before
    public void setUp() {
        setupProxyAndService();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfDeleteThrowsOnNullInput() {
        service.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfGetByIdThrowsOnNullInput() {
        service.getById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfDeleteByIdThrowsOnNullInput() {
        service.deleteById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfCreateOrUpdateThrowsOnNullInput() {
        service.createOrUpdate(null);
    }

    protected abstract void setupProxyAndService();
}