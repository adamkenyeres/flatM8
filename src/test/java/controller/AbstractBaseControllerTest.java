package controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import service.AbstractBaseService;

import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractBaseControllerTest<T> {

    protected AbstractBaseController<T> controller;
    protected AbstractBaseController<T> emptyController;

    @Mock
    protected AbstractBaseService<T> service;

    @Mock
    protected AbstractBaseService<T> emptyService;

    @Before
    public abstract void setUp();

    @Test
    public void testIfGetAllEntitiesReturnCorrectResponse() {
        ResponseEntity responseEntity = controller.getAllEntities();
        ResponseEntity responseEntity1 = emptyController.getAllEntities();
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Test
    public void testIfGetEntityByIdReturnCorrectResponse() {
        ResponseEntity responseEntity = controller.getEntityById("ID");
        ResponseEntity responseEntity1 = emptyController.getEntityById("ID");
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Test
    public void testIfCreateEntityReturnCorrectResponse() {
        ResponseEntity responseEntity = controller.createEntity(DUMMY_ENTITY);
        ResponseEntity responseEntity1 = emptyController.createEntity(DUMMY_ENTITY);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Test
    public void testIfDeleteEntityByIdReturnCorrectResponse() {
        ResponseEntity responseEntity = controller.deleteEntityById("ID");
        ResponseEntity responseEntity1 = emptyController.deleteEntityById("ID");
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Test
    public void testIfDeletedeleteAllEntitiesReturnCorrectResponse() {
        ResponseEntity responseEntity = controller.deleteEntityById("ID");
        ResponseEntity responseEntity1 = emptyController.deleteEntityById("ID");
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    protected abstract List<T> getDummyEntities();
    protected T DUMMY_ENTITY;
}