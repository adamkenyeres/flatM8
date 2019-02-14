package model.flat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.common.AdditionalDetail;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flat {

    @Id
    private String id;
    private Address address;
    private FlatType type;
    private Integer roomCount;
    private String userEmail;
    private List<AdditionalDetail> additionalDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return Objects.equals(address, flat.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
