package service;

import aop.NullCheckAspect;
import jersey.repackaged.com.google.common.collect.Lists;
import model.flat.Address;
import model.flat.Flat;
import model.request.AddMateRequest;
import model.request.DeleteFlatRequest;
import model.request.DeleteMateRequest;
import model.request.RequestStatus;
import model.tenant.User;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import repository.DeleteFlatRequestRepository;
import repository.DeleteMateRequestRepository;
import util.DummyDataGenerator;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class DeleteFlatRequestServiceTest extends AbstractRequestServiceTest<DeleteFlatRequest> {

    @Override
    protected List<DeleteFlatRequest> dummyRequests() {
        DeleteFlatRequest deleteFlatRequest = new DeleteFlatRequest();
        deleteFlatRequest.setReceivers(Lists.newArrayList(DummyDataGenerator.generateDummyUser()));
        deleteFlatRequest.setSender(DummyDataGenerator.generateDummyUser());
        deleteFlatRequest.setRequestStatus(RequestStatus.PENDING);
        deleteFlatRequest.setFlatToDelete(DummyDataGenerator.generateDummyFlat());

        Flat flat = DummyDataGenerator.generateDummyFlat();
        Address address = DummyDataGenerator.generateDummyAddress();
        address.setStreetAddress("New");
        flat.setAddress(address);

        DeleteFlatRequest deleteFlatRequest1 = new DeleteFlatRequest();
        deleteFlatRequest1.setReceivers(Lists.newArrayList(DummyDataGenerator.generateDummyUser()));
        deleteFlatRequest1.setSender(DummyDataGenerator.generateDummyUser());
        deleteFlatRequest1.setRequestStatus(RequestStatus.PENDING);
        deleteFlatRequest1.setFlatToDelete(flat);

        return Lists.newArrayList(deleteFlatRequest, deleteFlatRequest1);
    }

    @Override
    protected void setupServiceAndRepoMocks() {
        requestRepository = mock(DeleteFlatRequestRepository.class);
        userService = mock(UserService.class);
        spyService = spy(new DeleteFlatRequestService((DeleteFlatRequestRepository)requestRepository, userService));

        when(requestRepository.save(any(DeleteFlatRequest.class)))
                .thenReturn(dummyRequests().get(0));

        when(spyService.getRequestsForUsers(anyString()))
                .thenReturn(dummyRequests());

        when(spyService.getMyRequests(anyString()))
                .thenReturn(dummyRequests());

        when(requestRepository.findAll())
                .thenReturn(dummyRequests().subList(0, 1));

        AspectJProxyFactory proxy = new AspectJProxyFactory(new DeleteFlatRequestService((DeleteFlatRequestRepository)requestRepository, userService));
        proxy.addAspect(new NullCheckAspect());
        this.requestService = proxy.getProxy();
    }
}
