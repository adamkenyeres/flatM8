package service;

import aop.NullCheckAspect;
import com.google.common.collect.Lists;
import model.request.DeleteMateRequest;
import model.request.RequestStatus;
import model.tenant.User;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import repository.DeleteMateRequestRepository;
import util.DummyDataGenerator;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class DeleteMateRequestServiceTest extends AbstractRequestServiceTest<DeleteMateRequest> {

    @Override
    protected List<DeleteMateRequest> dummyRequests() {
        DeleteMateRequest deleteMateRequest = new DeleteMateRequest();
        deleteMateRequest.setReceivers(Lists.newArrayList(DummyDataGenerator.generateDummyUser()));
        deleteMateRequest.setSender(DummyDataGenerator.generateDummyUser());
        deleteMateRequest.setRequestStatus(RequestStatus.PENDING);
        deleteMateRequest.setMateToDelete(DummyDataGenerator.generateDummyUser());

        User user = DummyDataGenerator.generateDummyUser();
        user.setEmail("new@new.hu");
        DeleteMateRequest deleteMateRequest2 = new DeleteMateRequest();
        deleteMateRequest2.setReceivers(Lists.newArrayList(user));
        deleteMateRequest2.setSender(user);
        deleteMateRequest2.setRequestStatus(RequestStatus.PENDING);
        deleteMateRequest2.setMateToDelete(user);

        return Lists.newArrayList(deleteMateRequest, deleteMateRequest2);
    }

    @Override
    protected void setupServiceAndRepoMocks() {
        requestRepository = mock(DeleteMateRequestRepository.class);
        userService = mock(UserService.class);
        spyService = spy(new DeleteMateRequestService((DeleteMateRequestRepository)requestRepository, userService));

        when(requestRepository.save(nullable(DeleteMateRequest.class)))
                .thenReturn(dummyRequests().get(0));

        when(spyService.getRequestsForUsers(anyString()))
                .thenReturn(dummyRequests());

        when(spyService.getMyRequests(anyString()))
                .thenReturn(dummyRequests());

        when(requestRepository.findAll())
                .thenReturn(dummyRequests().subList(0, 1));

        AspectJProxyFactory proxy = new AspectJProxyFactory(new DeleteMateRequestService((DeleteMateRequestRepository)requestRepository, userService));
        proxy.addAspect(new NullCheckAspect());
        this.requestService = proxy.getProxy();
    }
}
