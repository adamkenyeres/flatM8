package model.flatmate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.criteria.BaseCriteria;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomCriteria {
    private BaseCriteria criteria;
    private Integer capacity;
    private Integer size;
    private List<String> additionalDetails;
}
