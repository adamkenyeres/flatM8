package service;

import aop.NullCheckAspect;
import com.google.common.collect.Lists;
import model.request.AddMateRequest;
import model.request.RequestStatus;
import model.tenant.User;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import repository.AddMateRequestRepository;
import util.DummyDataGenerator;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
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
        User user = DummyDataGenerator.generateDummyUser();
        addMateRequest.setReceivers(Lists.newArrayList(user));
        addMateRequest.setSender(user);
        addMateRequest.setRequestStatus(RequestStatus.PENDING);
        addMateRequest.setMateToAdd(user);

        User user2 = DummyDataGenerator.generateDummyUser("new@new.hu");
        AddMateRequest addMateRequest2 = new AddMateRequest();
        addMateRequest2.setReceivers(Lists.newArrayList(user2));
        addMateRequest2.setReceivers(Lists.newArrayList(user2));
        addMateRequest2.setSender(user2);
        addMateRequest2.setRequestStatus(RequestStatus.PENDING);
        addMateRequest2.setMateToAdd(user2);

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