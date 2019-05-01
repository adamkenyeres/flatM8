package model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.flatmate.FlatMateEntry;

import java.util.Objects;

@AllArgsConstructor
@Data
public class ContactRequest extends BaseRequest {

    private FlatMateEntry entry;

    public ContactRequest() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactRequest that = (ContactRequest) o;
        return Objects.equals(entry, that.entry)
                && Objects.equals(this.sender, that.sender);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), entry);
    }
}
