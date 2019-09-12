package controller;

import com.google.common.collect.Lists;
import model.request.DeleteMateRequest;
import model.tenant.User;
import org.glassfish.hk2.runlevel.RunLevelException;
import service.DeleteMateRequestService;
import util.DummyDataGenerator;

import java.util.Collections;
import java.util.List;

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
        when(emptyRequestService.getById(nullable(String.class)))
                .thenReturn(null);
        when(emptyRequestService.createOrUpdate(nullable(DeleteMateRequest.class)))
                .thenReturn(null);
        when(emptyRequestService.createRequestWithDuplicateCheck(nullable(DeleteMateRequest.class)))
                .thenReturn(null);
        when(emptyRequestService.updateUsersInRequests(nullable(User.class)))
                .thenReturn(Collections.emptyList());
        when(emptyRequestService.getRequestsForUsers(nullable(String.class)))
                .thenReturn(Collections.emptyList());
        when(emptyRequestService.getMyRequests(nullable(String.class)))
                .thenReturn(Collections.emptyList());
        doThrow(new RunLevelException())
                .when(emptyRequestService)
                .deleteById(nullable(String.class));
        doThrow(new RunLevelException())
                .when(emptyRequestService)
                .deleteAll();

        when(requestService.getAll())
                .thenReturn(getDummyEntities());
        when(requestService.getById(nullable(String.class)))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.createOrUpdate(nullable(DeleteMateRequest.class)))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.createRequestWithDuplicateCheck(nullable(DeleteMateRequest.class)))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.updateUsersInRequests(nullable(User.class)))
                .thenReturn(getDummyEntities());
        when(requestService.getRequestsForUsers(nullable(String.class)))
                .thenReturn(getDummyEntities());
        when(requestService.getMyRequests(nullable(String.class)))
                .thenReturn(getDummyEntities());
        doNothing()
                .when(requestService)
                .deleteById(nullable(String.class));
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