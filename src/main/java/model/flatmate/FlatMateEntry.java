package model.flatmate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.criteria.BaseCriteria;
import model.flat.Flat;
import model.tenant.User;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlatMateEntry {

    @Id
    private String id;
    private Flat flat;
    private User mainTenant;
    private List<User> tenants;
    private List<RoomCriteria> roomCriterias;
    private Integer remainingFreeSpaceCount;

    public void addRoomWithCriteria(Integer roomId, BaseCriteria baseCriteria) {
        RoomCriteria roomCriteria = new RoomCriteria(roomId, baseCriteria);
        roomCriterias.add(roomCriteria);
    }

    public void addTenant(User tenant) {
        tenants.add(tenant);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlatMateEntry that = (FlatMateEntry) o;
        return Objects.equals(flat, that.flat);
    }

    @Override
    public int hashCode() {

        return Objects.hash(flat);
    }
}
