package service;

import annotation.ImplicitNullCheck;
import com.google.common.collect.ImmutableList;
import model.request.BaseRequest;
import model.request.RequestStatus;
import model.tenant.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BaseRequestRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public abstract class AbstractRequestService<T extends BaseRequest> extends AbstractBaseService<T> {

    private final BaseRequestRepository<T> baseRequestRepository;
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRequestService.class);

    @Autowired
    public AbstractRequestService(UserService userService, BaseRequestRepository<T> baseRequestRepository) {
        super(baseRequestRepository);
        this.userService = userService;
        this.baseRequestRepository = baseRequestRepository;
    }

    @ImplicitNullCheck
    public List<T> getRequestsForUsers(String email) {
        return getRequestsForUsers(ImmutableList.of(email));
    }

    @ImplicitNullCheck
    public List<T> getRequestsForUsers(List<String> emails) {

        List<User> users = userService.getUsersByEmail(emails);
        return baseRequestRepository.findAllByReceiversIn(users);
    }

    @ImplicitNullCheck
    public List<T> getMyRequests(String email) {
        User user = userService.getUserByEmail(email);
        return baseRequestRepository.findAllBySender(user);
    }

    @ImplicitNullCheck
    public T createRequestWithDuplicateCheck(T entry) {
        boolean existing = repository.findAll()
                .stream()
                .anyMatch(r -> r.equals(entry)
                        && r.getRequestStatus().equals(RequestStatus.PENDING));

        if (existing) {
            return null;
        }
        return repository.save(entry);
    }

    @ImplicitNullCheck
    public List<T> updateUsersInRequests(User user) {
        List<T> requests = getRequestsForUsers(user.getEmail());

        if (isEmpty(requests)) {
            return Collections.emptyList();
        } else {
            requests.forEach(r -> {
                r.getReceivers().remove(user);
                r.getReceivers().add(user);

                if (r.getSender().equals(user)) {
                    r.setSender(user);
                }
            });

            requests.forEach(this::createOrUpdate);
            return requests;
        }
    }

    public List<T> updateMyRequestsForUser(User user) {
        List<T> myRequests = getMyRequests(user.getEmail());

        if (isEmpty(myRequests)) {
            return Collections.emptyList();
        } else {
            myRequests.forEach(r -> {
                r.setSender(user);
                if (r.getReceivers().contains(user)) {
                    r.getReceivers().remove(user);
                    r.getReceivers().add(user);
                }
            });
            myRequests.forEach(this::createOrUpdate);
            return myRequests;
        }
    }
}
