package controller;

import jersey.repackaged.com.google.common.collect.Lists;
import model.request.DeleteFlatRequest;
import model.request.DeleteMateRequest;
import model.tenant.User;
import org.glassfish.hk2.runlevel.RunLevelException;
import service.DeleteFlatRequestService;
import service.DeleteMateRequestService;
import util.DummyDataGenerator;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class DeleteMateRequestControllerTest extends AbstractRequestControllerTest<DeleteMateRequest> {
    @Override
    public void setUp() {
        this.requestService = mock(DeleteMateRequestService.class);
        this.emptyRequestService = mock(DeleteMateRequestService.class);

        this.requestController = new DeleteMateRequestController((DeleteMateRequestService)requestService);
        this.emptyRequestController = new DeleteMateRequestController((DeleteMateRequestService)emptyRequestService);

        when(emptyRequestService.getAll())
                .thenReturn(Collections.emptyList());
        when(emptyRequestService.getById(anyString()))
                .thenReturn(null);
        when(emptyRequestService.createOrUpdate(any(DeleteMateRequest.class)))
                .thenReturn(null);
        when(emptyRequestService.createRequestWithDuplicateCheck(any(DeleteMateRequest.class)))
                .thenReturn(null);
        when(emptyRequestService.updateUsersInRequests(any(User.class)))
                .thenReturn(Collections.emptyList());
        when(emptyRequestService.getRequestsForUsers(anyString()))
                .thenReturn(Collections.emptyList());
        when(emptyRequestService.getMyRequests(anyString()))
                .thenReturn(Collections.emptyList());
        doThrow(new RunLevelException())
                .when(emptyRequestService)
                .deleteById(anyString());
        doThrow(new RunLevelException())
                .when(emptyRequestService)
                .deleteAll();

        when(requestService.getAll())
                .thenReturn(getDummyEntities());
        when(requestService.getById(anyString()))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.createOrUpdate(any(DeleteMateRequest.class)))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.createRequestWithDuplicateCheck(any(DeleteMateRequest.class)))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.updateUsersInRequests(any(User.class)))
                .thenReturn(getDummyEntities());
        when(requestService.getRequestsForUsers(anyString()))
                .thenReturn(getDummyEntities());
        when(requestService.getMyRequests(anyString()))
                .thenReturn(getDummyEntities());
        doNothing()
                .when(requestService)
                .deleteById(anyString());
        doNothing()
                .when(requestService)
                .deleteAll();
        this.service = requestService;
        this.emptyService = emptyRequestService;
        this.controller = requestController;
        this.emptyController = emptyRequestController;
    }

    @Override
    protected List<DeleteMateRequest> getDummyEntities() {
        return Lists.newArrayList(DummyDataGenerator.generateDeleteMateRequest());
    }
}