package controller;

import model.request.BaseRequest;
import model.tenant.User;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import service.AbstractRequestService;
import util.DummyDataGenerator;

import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractRequestControllerTest<T extends BaseRequest> extends AbstractBaseControllerTest<T> {

    protected AbstractRequestService<T> requestService;
    protected AbstractRequestService<T> emptyRequestService;
    protected AbstractRequestController<T> requestController;
    protected AbstractRequestController<T> emptyRequestController;

    @Override
    public abstract void setUp();

    @Override
    protected abstract List<T> getDummyEntities();

    @Test
    public void testIfUpdateUserReturnCorrectResponse() {
        ResponseEntity responseEntity = requestController.updateUser(DummyDataGenerator.generateDummyUserSingleton());
        ResponseEntity responseEntity1 = emptyRequestController.updateUser(DummyDataGenerator.generateDummyUserSingleton());
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Test
    public void testIfGetMyRequestsReturnCorrectResponse() {
        ResponseEntity responseEntity = requestController.getMyRequests("email");
        ResponseEntity responseEntity1 = emptyRequestController.getMyRequests("email");
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }


    @Test
    public void testIfGetForUserReturnCorrectResponse() {
        ResponseEntity responseEntity = requestController.getForUser("email");
        ResponseEntity responseEntity1 = emptyRequestController.getForUser("email");
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }


    @Test
    public void testIfUpdateEntityReturnCorrectResponse() {
        ResponseEntity responseEntity = requestController.updateEntity(DUMMY_ENTITY);
        ResponseEntity responseEntity1 = emptyRequestController.updateEntity(DUMMY_ENTITY);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }
}