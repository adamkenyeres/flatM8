package model.request;

import lombok.Data;
import model.flat.Flat;

import java.util.Objects;

@Data
public class DeleteFlatRequest extends BaseRequest {
    private Flat flatToDelete;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteFlatRequest that = (DeleteFlatRequest) o;
        return Objects.equals(flatToDelete, that.flatToDelete)
                && Objects.equals(this.sender, that.sender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), flatToDelete);
    }

}
