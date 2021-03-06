package service;

import aop.NullCheckAspect;
import exception.MultipleFlatForUserException;
import com.google.common.collect.Lists;
import model.flat.Flat;
import model.tenant.User;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import repository.FlatRepository;
import repository.UserRepository;
import util.DummyDataGenerator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FlatServiceTest extends AbstractBaseServiceTest<Flat> {

    @Mock
    private UserRepository userRepository;

    private FlatService flatService;

    @Override
    protected void setupProxyAndService() {
        repository = mock(FlatRepository.class);
        userRepository = mock(UserRepository.class);

        when(((FlatRepository) repository).findByUserEmail(nullable(String.class)))
                .thenReturn(DummyDataGenerator.generateDummyFlat());

        when(repository.save(nullable(Flat.class)))
                .then(returnsFirstArg());

        when(repository.findAll())
                .thenReturn(Lists.newArrayList(DummyDataGenerator.generateDummyFlat()));

        when(userRepository.findByEmail(nullable(String.class)))
                .thenReturn(DummyDataGenerator.generateDummyUser());

        AspectJProxyFactory proxy =
                new AspectJProxyFactory(new FlatService((FlatRepository)repository, userRepository));
        proxy.addAspect(new NullCheckAspect());
        this.service = proxy.getProxy();
        this.flatService = proxy.getProxy();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfSaveFlatThrowsIfUserEmailNull() throws Exception {
        Flat f = new Flat();
        flatService.saveFlat(f);
    }

    @Test(expected = MultipleFlatForUserException.class)
    public void testIfMultipleFlatsCantBeSavedForSameUser() throws Exception {
        Flat f = new Flat();
        f.setUserEmail("test@test.hu");
        flatService.saveFlat(f);
    }

    @Test
    public void testIfUserUpdatedForFlat() {
        User u = DummyDataGenerator.generateDummyUser();
        u.setFirstName("UPDATED");
        Flat f = flatService.updateFlatWithUser(u);
        assertThat(f.getFlatMates().get(0).getFirstName(), is("UPDATED"));
    }
}