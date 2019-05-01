package service;

import aop.NullCheckAspect;
import jersey.repackaged.com.google.common.collect.Lists;
import model.request.AddMateRequest;
import model.request.BaseRequest;
import model.request.RequestStatus;
import model.tenant.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import repository.AddMateRequestRepository;
import repository.BaseRequestRepository;
import util.DummyDataGenerator;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public abstract class AbstractRequestServiceTest<T extends BaseRequest> {

    protected AbstractRequestService<T> requestService;

    @Mock
    protected UserService userService;

    @Mock
    protected BaseRequestRepository<T> requestRepository;

    @Spy
    protected AbstractRequestService<T> spyService;

    @Before
    public void setUp() {
        setupServiceAndRepoMocks();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfGetRequestsThrowsOnNullInput() {
        requestService.getRequestsForUsers((List<String>)null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfGetMyRequestsThrowsOnNullInput2() {
        requestService.getRequestsForUsers((String)null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIfCreateRequestWithDuplicateCheckThrowsOnNullInput() {
        requestService.createRequestWithDuplicateCheck(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfUpdateUsersInRequestsThrowsOnNullInput() {
        requestService.updateUsersInRequests(null);
    }

    @Test
    public void checkIfDuplicateRequestCantBeCreated() {
        T addMateRequest = spyService.createRequestWithDuplicateCheck(dummyRequests().get(0));

        assertThat(addMateRequest, is(nullValue()));

        addMateRequest = spyService.createRequestWithDuplicateCheck(dummyRequests().get(1));

        assertThat(addMateRequest, notNullValue());
    }

    @Test
    public void testIfUpdatingUserInRequestsUpdatesUserIfStateChanged() {
        User updatedUser = DummyDataGenerator.generateDummyUser();
        updatedUser.setFirstName("UPDATED");

        List<T> updatedRequests = spyService.updateUsersInRequests(updatedUser);
        List<T> updatedMyRequests = spyService.updateMyRequestsForUser(updatedUser);

        T updatedRequest = updatedRequests.get(0);

        assertThat(updatedRequest.getSender().getFirstName(), is("UPDATED"));
        assertThat(updatedRequest.getReceivers().get(0).getFirstName(), is("UPDATED"));

        T updatedMyRequest = updatedMyRequests.get(0);

        assertThat(updatedMyRequest.getSender().getFirstName(), is("UPDATED"));
        assertThat(updatedMyRequest.getReceivers().get(0).getFirstName(), is("UPDATED"));

    }

    protected abstract List<T> dummyRequests();
    protected abstract void setupServiceAndRepoMocks();
}