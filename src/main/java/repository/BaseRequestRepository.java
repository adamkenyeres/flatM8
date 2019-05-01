package repository;

import model.request.BaseRequest;
import model.tenant.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BaseRequestRepository<T extends BaseRequest> extends MongoRepository<T, String> {
    List<T> findAllByApproversIn(List<User> approvers);
    List<T> findAllByReceiversIn(List<User> receivers);
    List<T> findAllBySender(User sender);
}
