package controller;

import exception.FlatNotFoundException;
import jersey.repackaged.com.google.common.collect.Lists;
import model.chat.ChatMessage;
import model.flat.Address;
import model.flat.Flat;
import model.tenant.User;
import org.glassfish.hk2.runlevel.RunLevelException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import service.ChatMessageService;
import service.FlatService;
import util.DummyDataGenerator;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class FlatControllerTest extends AbstractBaseControllerTest<Flat> {

    private FlatController flatController;
    private FlatController emptyFlatController;

    @Override
    @Before
    public void setUp() {
        this.service = mock(FlatService.class);
        this.emptyService = mock(FlatService.class);
        FlatService flatService = mock(FlatService.class);
        FlatService emptyFlatService = mock(FlatService.class);

        flatController = new FlatController(flatService);
        emptyFlatController = new FlatController(emptyFlatService);

        when(emptyService.getAll())
                .thenReturn(Collections.emptyList());
        when(emptyService.getById(anyString()))
                .thenReturn(null);
        when(emptyService.createOrUpdate(any(Flat.class)))
                .thenReturn(null);
        doThrow(new RuntimeException())
                .when(emptyService)
                .deleteById(anyString());
        doThrow(new RuntimeException())
                .when(emptyService)
                .deleteAll();
        when(emptyFlatService.updateFlat(any(Flat.class)))
                .thenReturn(null);
        when(emptyFlatService.getFlatForFlatMate(anyString()))
                .thenReturn(null);
        when(emptyFlatService.updateFlatWithUser(any(User.class)))
                .thenReturn(null);
        when(emptyFlatService.getFlatByAddress(any(Address.class)))
                .thenReturn(null);
        try {
            doThrow(new FlatNotFoundException("NOPE"))
                    .when(emptyFlatService)
                    .deleteFlatByAddress(any(Address.class));
        } catch (FlatNotFoundException e) {
            assert false;
        }


        when(service.getAll())
                .thenReturn(getDummyEntities());
        when(service.getById(anyString()))
                .thenReturn(getDummyEntities().get(0));
        when(service.createOrUpdate(any(Flat.class)))
                .thenReturn(getDummyEntities().get(0));
        doNothing()
                .when(service)
                .deleteById(anyString());
        doNothing()
                .when(service)
                .deleteAll();
        when(flatService.updateFlat(any(Flat.class)))
                .thenReturn(getDummyEntities().get(0));
        when(flatService.getFlatForFlatMate(anyString()))
                .thenReturn(getDummyEntities().get(0));
        when(flatService.updateFlatWithUser(any(User.class)))
                .thenReturn(getDummyEntities().get(0));
        when(flatService.getFlatByAddress(any(Address.class)))
                .thenReturn(getDummyEntities().get(0));
        try {
            doNothing()
                    .when(flatService)
                    .deleteFlatByAddress(any(Address.class));
        } catch (FlatNotFoundException e) {
            assert false;
        }

        this.controller = new FlatController((FlatService) service);
        this.emptyController = new FlatController((FlatService)emptyService);
    }

    @Test
    public void testIfUpdateEntityReturnCorrectResponse() {
        ResponseEntity responseEntity = flatController.updateEntity(null);
        ResponseEntity responseEntity1 = emptyFlatController.updateEntity(null);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Test
    public void testIfDeleteFlatByAddressReturnCorrectResponse() {
        ResponseEntity responseEntity = flatController.deleteFlatByAddress(null);
        ResponseEntity responseEntity1 = emptyFlatController.deleteFlatByAddress(null);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }


    @Test
    public void testIfGetFlatByAddressReturnCorrectResponse() {
        ResponseEntity responseEntity = flatController.getFlatByAddress(null);
        ResponseEntity responseEntity1 = emptyFlatController.getFlatByAddress(null);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Test
    public void testIfGetFlatForUserReturnCorrectResponse() {
        ResponseEntity responseEntity = flatController.getFlatForUser(null);
        ResponseEntity responseEntity1 = emptyFlatController.getFlatForUser(null);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Test
    public void testIfUpdateUserInFlatReturnCorrectResponse() {
        ResponseEntity responseEntity = flatController.updateUserInFlat(null);
        ResponseEntity responseEntity1 = emptyFlatController.updateUserInFlat(null);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(400, responseEntity1.getStatusCode().value());
    }


    @Override
    protected List<Flat> getDummyEntities() {
        return Lists.newArrayList(DummyDataGenerator.generateDummyFlat());
    }
}