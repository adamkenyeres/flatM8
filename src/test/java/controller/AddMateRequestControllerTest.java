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
        when(emptyRequestService.getById(any(String.class)))
                .thenReturn(null);
        when(emptyRequestService.createOrUpdate(any(AddMateRequest.class)))
                .thenReturn(null);
        when(emptyRequestService.createRequestWithDuplicateCheck(any(AddMateRequest.class)))
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
        when(requestService.createOrUpdate(any(AddMateRequest.class)))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.createRequestWithDuplicateCheck(any(AddMateRequest.class)))
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
        this.DUMMY_ENTITY = new AddMateRequest();
    }

    @Override
    protected List<AddMateRequest> getDummyEntities() {
        return Lists.newArrayList(DummyDataGenerator.generateDummyAddMateRequest());
    }
}