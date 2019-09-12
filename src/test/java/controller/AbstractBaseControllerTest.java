package controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import service.AbstractBaseService;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.assertj.core.api.Assertions.assertThat;
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
        ResponseEntity responseEntity = controller.getEntityById("X");
        ResponseEntity responseEntity1 = emptyController.getEntityById("X");
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Test
    public void testIfCreateEntityReturnCorrectResponse() {
        ResponseEntity responseEntity = controller.createEntity(null);
        ResponseEntity responseEntity1 = emptyController.createEntity(null);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Test
    public void testIfDeleteEntityByIdReturnCorrectResponse() {
        ResponseEntity responseEntity = controller.deleteEntityById("xd");
        ResponseEntity responseEntity1 = emptyController.deleteEntityById("xd");
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Test
    public void testIfDeletedeleteAllEntitiesReturnCorrectResponse() {
        ResponseEntity responseEntity = controller.deleteEntityById("xd");
        ResponseEntity responseEntity1 = emptyController.deleteEntityById("xd");
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    protected abstract List<T> getDummyEntities();
}