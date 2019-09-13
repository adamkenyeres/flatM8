package controller;

import com.google.common.collect.Lists;
import model.request.DeleteFlatRequest;
import model.tenant.User;
import org.glassfish.hk2.runlevel.RunLevelException;
import service.DeleteFlatRequestService;
import util.DummyDataGenerator;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class DeleteFlatRequestControllerTest extends AbstractRequestControllerTest<DeleteFlatRequest> {

    @Override
    public void setUp() {
        this.requestService = mock(DeleteFlatRequestService.class);
        this.emptyRequestService = mock(DeleteFlatRequestService.class);

        this.requestController = new DeleteFlatRequestController((DeleteFlatRequestService)requestService);
        this.emptyRequestController = new DeleteFlatRequestController((DeleteFlatRequestService)emptyRequestService);

        when(emptyRequestService.getAll())
                .thenReturn(Collections.emptyList());
        when(emptyRequestService.getById(any(String.class)))
                .thenReturn(null);
        when(emptyRequestService.createOrUpdate(any(DeleteFlatRequest.class)))
                .thenReturn(null);
        when(emptyRequestService.createRequestWithDuplicateCheck(any(DeleteFlatRequest.class)))
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
        when(requestService.createOrUpdate(any(DeleteFlatRequest.class)))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.createRequestWithDuplicateCheck(any(DeleteFlatRequest.class)))
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
        this.DUMMY_ENTITY = new DeleteFlatRequest();
    }

    @Override
    protected List<DeleteFlatRequest> getDummyEntities() {
        return Lists.newArrayList(DummyDataGenerator.generateDeleteFlatRequest());
    }
}