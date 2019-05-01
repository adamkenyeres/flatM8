package model.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.chat.ChatContact;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    private String id;

    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private TenantType tenantType;
    private String tenantTypeComment;
    private TenantStayType tenantStayType;
    private String tenantStayTypeComment;
    private int age;
    private Set<Role> roles;
    private Set<ChatContact> contacts;
    private String avatarPath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
