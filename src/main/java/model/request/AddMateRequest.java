package model.request;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.flat.Flat;
import model.tenant.User;

import java.util.Objects;

@Getter
@Setter
public class AddMateRequest extends BaseRequest {

    private User mateToAdd;
    private Flat flat;

    public AddMateRequest() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddMateRequest that = (AddMateRequest) o;
        return Objects.equals(mateToAdd, that.mateToAdd) &&
                Objects.equals(flat, that.flat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mateToAdd, flat);
    }
}
