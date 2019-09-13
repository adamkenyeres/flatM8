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
        when(emptyRequestService.getById(any(String.class)))
                .thenReturn(null);
        when(emptyRequestService.createOrUpdate(any(DeleteMateRequest.class)))
                .thenReturn(null);
        when(emptyRequestService.createRequestWithDuplicateCheck(any(DeleteMateRequest.class)))
                .thenReturn(null);
        when(emptyRequestService.updateUsersInRequests(any(User.class)))
                .thenReturn(Collections.emptyList());
        when(emptyRequestService.getRequestsForUsers(any(String.class)))
                .thenReturn(Collections.emptyList());
        when(emptyRequestService.getMyRequests(any(String.class)))
                .thenReturn(Collections.emptyList());
        doThrow(new RunLevelException())
                .when(emptyRequestService)
                .deleteById(any(String.class));
        doThrow(new RunLevelException())
                .when(emptyRequestService)
                .deleteAll();

        when(requestService.getAll())
                .thenReturn(getDummyEntities());
        when(requestService.getById(any(String.class)))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.createOrUpdate(any(DeleteMateRequest.class)))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.createRequestWithDuplicateCheck(any(DeleteMateRequest.class)))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.updateUsersInRequests(any(User.class)))
                .thenReturn(getDummyEntities());
        when(requestService.getRequestsForUsers(any(String.class)))
                .thenReturn(getDummyEntities());
        when(requestService.getMyRequests(any(String.class)))
                .thenReturn(getDummyEntities());
        doNothing()
                .when(requestService)
                .deleteById(any(String.class));
        doNothing()
                .when(requestService)
                .deleteAll();
        this.service = requestService;
        this.emptyService = emptyRequestService;
        this.controller = requestController;
        this.emptyController = emptyRequestController;
        this.DUMMY_ENTITY = new DeleteMateRequest();
    }

    @Override
    protected List<DeleteMateRequest> getDummyEntities() {
        return Lists.newArrayList(DummyDataGenerator.generateDeleteMateRequest());
    }
}