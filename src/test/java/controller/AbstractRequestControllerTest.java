package controller;

import model.request.BaseRequest;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import service.AbstractRequestService;

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
        ResponseEntity responseEntity = requestController.updateUser(null);
        ResponseEntity responseEntity1 = emptyRequestController.updateUser(null);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Test
    public void testIfGetMyRequestsReturnCorrectResponse() {
        ResponseEntity responseEntity = requestController.getMyRequests(null);
        ResponseEntity responseEntity1 = emptyRequestController.getMyRequests(null);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }


    @Test
    public void testIfGetForUserReturnCorrectResponse() {
        ResponseEntity responseEntity = requestController.getForUser(null);
        ResponseEntity responseEntity1 = emptyRequestController.getForUser(null);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }


    @Test
    public void testIfUpdateEntityReturnCorrectResponse() {
        ResponseEntity responseEntity = requestController.updateEntity(null);
        ResponseEntity responseEntity1 = emptyRequestController.updateEntity(null);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }
}