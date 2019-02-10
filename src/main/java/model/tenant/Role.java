package model.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private Long id;
    private String name;
    private Set<User> users;
}
