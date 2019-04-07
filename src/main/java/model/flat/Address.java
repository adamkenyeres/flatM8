package model.flat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String city;
    private String streetAddress;
    private Integer streetNumber;
    private Integer district;
    private Integer door;
    private Integer floor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) &&
                Objects.equals(streetAddress, address.streetAddress) &&
                Objects.equals(streetNumber, address.streetNumber) &&
                Objects.equals(district, address.district) &&
                Objects.equals(door, address.door) &&
                Objects.equals(floor, address.floor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, streetAddress, streetNumber, district, door, floor);
    }
}
