package model.request;


import lombok.Data;
import lombok.EqualsAndHashCode;
import model.flat.Flat;
import model.flatmate.FlatMateEntry;
import model.tenant.User;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Objects;

@Data
public class DeleteMateRequest extends BaseRequest {

    private User mateToDelete;
    private Flat flat;

    public DeleteMateRequest() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteMateRequest that = (DeleteMateRequest) o;
        return Objects.equals(mateToDelete, that.mateToDelete) &&
                Objects.equals(flat, that.flat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mateToDelete, flat);
    }
}
