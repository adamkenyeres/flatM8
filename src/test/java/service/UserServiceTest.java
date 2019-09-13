package service;

import aop.NullCheckAspect;
import model.tenant.User;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.RoleRepository;
import repository.UserRepository;

import static org.mockito.Mockito.mock;

public class UserServiceTest extends AbstractBaseServiceTest<User> {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService userService;

    @Override
    protected void setupProxyAndService() {
        repository = mock(UserRepository.class);
        AspectJProxyFactory proxy =
                new AspectJProxyFactory(new UserService((UserRepository)repository, roleRepository, bCryptPasswordEncoder));
        proxy.addAspect(new NullCheckAspect());
        this.service = proxy.getProxy();
        this.userService = proxy.getProxy();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfRegisterThrowsOnNullInput() throws Exception {
        userService.register(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfFetUserByEmailThrowsOnNullInput() {
        userService.getUserByEmail(null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testIfGetUsersByEmailThrowsOnNullInput() {
        userService.getUsersByEmail(null);
    }
}