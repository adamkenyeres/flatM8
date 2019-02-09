package controller;

import model.flat.Flat;
import model.flat.FlatType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ComponentScan(basePackages = "controller")
@Rollback
public class FlatControllerTest {

    @Autowired
    private FlatController flatController;

    @Test
    public void testIfNewEntriesAreCreated() {
        Flat flat = new Flat();
        flat.setType(FlatType.HOUSE);
        Flat retFlat = flatController.createFlat(flat);


        assertThat(retFlat.getId(), notNullValue());
        assertThat(retFlat.getType(), equalTo(flat.getType()));
    }
}
