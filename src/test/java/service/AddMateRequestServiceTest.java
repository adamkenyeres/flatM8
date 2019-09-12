package service;

import aop.NullCheckAspect;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import model.request.AddMateRequest;
import model.request.RequestStatus;
import model.tenant.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import repository.AddMateRequestRepository;
import util.DummyDataGenerator;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class AddMateRequestServiceTest extends AbstractRequestServiceTest<AddMateRequest> {

    public AddMateRequestServiceTest() {
        AspectJProxyFactory proxy = new AspectJProxyFactory(new AddMateRequestService((AddMateRequestRepository)requestRepository, userService));
        proxy.addAspect(new NullCheckAspect());
        this.requestService = proxy.getProxy();
    }

    @Override
    protected List<AddMateRequest> dummyRequests() {
        AddMateRequest addMateRequest = new AddMateRequest();
        addMateRequest.setReceivers(Lists.newArrayList(DummyDataGenerator.generateDummyUser()));
        addMateRequest.setSender(DummyDataGenerator.generateDummyUser());
        addMateRequest.setRequestStatus(RequestStatus.PENDING);
        addMateRequest.setMateToAdd(DummyDataGenerator.generateDummyUser());

        User user = DummyDataGenerator.generateDummyUser();
        user.setEmail("new@new.hu");
        AddMateRequest addMateRequest2 = new AddMateRequest();
        addMateRequest2.setReceivers(Lists.newArrayList(user));
        addMateRequest2.setSender(user);
        addMateRequest2.setRequestStatus(RequestStatus.PENDING);
        addMateRequest2.setMateToAdd(user);

        return Lists.newArrayList(addMateRequest, addMateRequest2);
    }

    @Override
    protected void setupServiceAndRepoMocks() {
        requestRepository = mock(AddMateRequestRepository.class);
        userService = mock(UserService.class);
        spyService = spy(new AddMateRequestService((AddMateRequestRepository)requestRepository, userService));

        when(requestRepository.save(any(AddMateRequest.class)))
                .thenReturn(dummyRequests().get(0));

        when(spyService.getRequestsForUsers(anyString()))
                .thenReturn(dummyRequests());

        when(spyService.getMyRequests(anyString()))
                .thenReturn(dummyRequests());

        when(requestRepository.findAll())
                .thenReturn(dummyRequests().subList(0, 1));
    }


}