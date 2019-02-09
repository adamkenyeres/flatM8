package model.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseCriteria {
    private GenderCriteria genderCriteria;
    private LifestyleCriteria lifestyleCriteria;
    private Integer ageCriteria;
    private RoomTypeCriteria roomTypeCriteria;
}
