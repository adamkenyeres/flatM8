package service;

import model.request.*;
import model.tenant.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public abstract class AbstractRequestService<T extends BaseRequest> {

    protected final MongoRepository<T, String> repository;

    protected final UserService userService;

    @Autowired
    public AbstractRequestService(MongoRepository<T, String> repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public List<T> getRequests() {
        return repository.findAll();
    }

    public List<T> getRequestsForUsers(String e) {
        return getRequestsForUsers(Collections.singleton(e));
    }

    public List<T> getRequestsForUsers(Collection<String> emails) {

        // TODO Bulk query by username
        List<User> users = emails.stream()
                .map(userService::findByUsername)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (isEmpty(users)) {
            return Collections.emptyList();
        }

        List<T> requests = repository.findAll();
        return requests.stream()
                .filter(r -> r.getReceivers().containsAll(users))
                .collect(Collectors.toList());
    }

    public List<T> getMyRequests(String e) {
        User user = userService.findByUsername(e);

        if (user == null) {
            return Collections.emptyList();
        }
        List<T> requests = repository.findAll();
        return requests.stream()
                .filter(r -> r.getSender().equals(user))
                .collect(Collectors.toList());
    }

    public List<T> getRequestsForUserByStatus(User u, RequestStatus requestStatus) {
        return getRequestsForUsersByStatus(Collections.singleton(u), requestStatus);
    }

    public List<T> getRequestsForUsersByStatus(Collection<User> u, RequestStatus requestStatus) {
        List<T> requests = repository.findAll();

        return requests.stream()
                .filter(r -> r.getRequestStatus().equals(requestStatus))
                .filter(r -> r.getReceivers().containsAll(u))
                .collect(Collectors.toList());
    }

    public T saveRequest(T request) {
        return repository.save(request);
    }

    public void deleteRequest(T request) {
        repository.delete(request);
    }

    public void deleteRequests() {
        repository.deleteAll();
    }
}
