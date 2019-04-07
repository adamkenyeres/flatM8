package model.tenant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.common.AdditionalDetail;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;
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
    private List<AdditionalDetail> additionalDetails;
    private Set<Role> roles;

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

    public boolean sameType(User other) {
        return other != null
                && this.getTenantType() != null
                && this.getTenantType().equals(tenantType);
    }

    public boolean sameStayType(User other) {
        return other != null
                && this.getTenantStayType() != null
                && this.getTenantStayType().equals(tenantStayType);
    }

    public boolean ageMatches(User other, int tolerance) {
        return Math.abs(age - other.age) <= tolerance;
    }

}
