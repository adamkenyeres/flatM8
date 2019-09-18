package controller;
import model.flat.Flat;
import model.tenant.User;
import org.graalvm.compiler.lir.LIRInstruction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import service.AbstractBaseService;
import service.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

public class UserControllerTest extends AbstractBaseControllerTest<User> {
    private UserController userController;
    private UserController emptyUserController;

    @Override
    @Before
    public void setUp() {
        this.service = mock(UserService.class);
        this.emptyService = mock(UserService.class);

        UserService userService = mock(UserService.class);
        UserService emptyUserService = mock(UserService.class);

        when(emptyService.getAll())
                .thenReturn(Collections.emptyList());
        when(emptyService.getById(nullable(String.class)))
                .thenReturn(null);
        when(emptyService.createOrUpdate(nullable(User.class)))
                .thenReturn(null);
        doThrow(new RuntimeException())
                .when(emptyService)
                .deleteById(nullable(String.class));
        doThrow(new RuntimeException())
                .when(emptyService)
                .deleteAll();

        when(emptyUserService.getUserByEmail(nullable(String.class)))
                .thenReturn(nullable(User.class));
        when(emptyUserService.getUserByUserName(nullable(String.class)))
                .thenReturn(nullable(User.class));
        when(emptyUserService.getLoggedInEmail())
                .thenReturn(nullable(String.class));
    }

    @Test
    public void testIfUserCanLogInWithUserName() {

    }
    @Override
    protected List<User> getDummyEntities() {
        return null;
    }
}
