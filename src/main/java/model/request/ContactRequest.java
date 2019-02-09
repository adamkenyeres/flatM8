package model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.flatmate.FlatMateEntry;
import model.tenant.User;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    @Id
    private String id;

    private User sender;
    private FlatMateEntry entry;
    private ContactRequestStatus overallStatus;
    private List<User> accepters;
    private List<User> rejecters;
}
