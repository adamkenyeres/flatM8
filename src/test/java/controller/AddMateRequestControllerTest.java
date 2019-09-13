package controller;

import com.google.common.collect.Lists;
import model.request.AddMateRequest;
import model.tenant.User;
import org.glassfish.hk2.runlevel.RunLevelException;
import service.AddMateRequestService;
import util.DummyDataGenerator;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class AddMateRequestControllerTest extends AbstractRequestControllerTest<AddMateRequest> {

    @Override
    public void setUp() {
        this.requestService = mock(AddMateRequestService.class);
        this.emptyRequestService = mock(AddMateRequestService.class);

        this.requestController = new AddMateRequestController((AddMateRequestService)requestService);
        this.emptyRequestController = new AddMateRequestController((AddMateRequestService)emptyRequestService);

        when(emptyRequestService.getAll())
                .thenReturn(Collections.emptyList());
        when(emptyRequestService.getById(nullable(String.class)))
                .thenReturn(null);
        when(emptyRequestService.createOrUpdate(nullable(AddMateRequest.class)))
                .thenReturn(null);
        when(emptyRequestService.createRequestWithDuplicateCheck(nullable(AddMateRequest.class)))
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
        when(requestService.createOrUpdate(nullable(AddMateRequest.class)))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.createRequestWithDuplicateCheck(nullable(AddMateRequest.class)))
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
    protected List<AddMateRequest> getDummyEntities() {
        return Lists.newArrayList(DummyDataGenerator.generateDummyAddMateRequest());
    }
}