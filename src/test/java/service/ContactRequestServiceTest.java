package service;

import aop.NullCheckAspect;
import jersey.repackaged.com.google.common.collect.Lists;
import model.flat.Address;
import model.flat.Flat;
import model.request.ContactRequest;
import model.request.DeleteFlatRequest;
import model.request.RequestStatus;
import model.tenant.User;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import repository.ContactRequestRepository;
import repository.DeleteFlatRequestRepository;
import util.DummyDataGenerator;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ContactRequestServiceTest extends AbstractRequestServiceTest<ContactRequest> {

    @Override
    protected List<ContactRequest> dummyRequests() {
        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setReceivers(Lists.newArrayList(DummyDataGenerator.generateDummyUser()));
        contactRequest.setSender(DummyDataGenerator.generateDummyUser());
        contactRequest.setRequestStatus(RequestStatus.PENDING);
        contactRequest.setSender(DummyDataGenerator.generateDummyUser());

        User user = DummyDataGenerator.generateDummyUser();
        user.setEmail("new2@new2.hu");

        ContactRequest contactRequest1 = new ContactRequest();
        contactRequest1.setReceivers(Lists.newArrayList(DummyDataGenerator.generateDummyUser()));
        contactRequest1.setSender(DummyDataGenerator.generateDummyUser());
        contactRequest1.setRequestStatus(RequestStatus.PENDING);
        contactRequest1.setSender(user);

        return Lists.newArrayList(contactRequest, contactRequest1);
    }

    @Override
    protected void setupServiceAndRepoMocks() {
        requestRepository = mock(ContactRequestRepository.class);
        userService = mock(UserService.class);
        spyService = spy(new ContactRequestService((ContactRequestRepository)requestRepository, userService));

        when(requestRepository.save(any(ContactRequest.class)))
                .thenReturn(dummyRequests().get(0));

        when(spyService.getRequestsForUsers(anyString()))
                .thenReturn(dummyRequests());

        when(spyService.getMyRequests(anyString()))
                .thenReturn(dummyRequests());

        when(requestRepository.findAll())
                .thenReturn(dummyRequests().subList(0, 1));

        AspectJProxyFactory proxy = new AspectJProxyFactory(new ContactRequestService((ContactRequestRepository)requestRepository, userService));
        proxy.addAspect(new NullCheckAspect());
        this.requestService = proxy.getProxy();
    }
}