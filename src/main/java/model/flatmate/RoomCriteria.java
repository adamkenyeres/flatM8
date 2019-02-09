package model.flatmate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.criteria.BaseCriteria;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomCriteria {
    private Integer roomId;
    private BaseCriteria criteria;
}
