package controller;

import com.google.common.collect.Lists;
import model.chat.ChatMessage;
import model.request.AddMateRequest;
import model.tenant.User;
import org.glassfish.hk2.runlevel.RunLevelException;
import service.AddMateRequestService;
import service.ChatMessageService;
import util.DummyDataGenerator;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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
        when(emptyRequestService.getById(anyString()))
                .thenReturn(null);
        when(emptyRequestService.createOrUpdate(any(AddMateRequest.class)))
                .thenReturn(null);
        when(emptyRequestService.createRequestWithDuplicateCheck(any(AddMateRequest.class)))
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
        when(requestService.createOrUpdate(any(AddMateRequest.class)))
                .thenReturn(getDummyEntities().get(0));
        when(requestService.createRequestWithDuplicateCheck(any(AddMateRequest.class)))
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
    protected List<AddMateRequest> getDummyEntities() {
        return Lists.newArrayList(DummyDataGenerator.generateDummyAddMateRequest());
    }
}