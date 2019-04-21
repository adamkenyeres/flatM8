package model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.tenant.User;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRequest {

    @Id
    private String id;
    protected RequestStatus requestStatus;
    private RequestType requestType;
    protected List<User> receivers;
    protected User sender;
    private List<User> approvers;
    private List<User> rejecters;
}
