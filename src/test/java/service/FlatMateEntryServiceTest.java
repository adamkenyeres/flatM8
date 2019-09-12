package service;

import aop.NullCheckAspect;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import model.chat.ChatContact;
import model.chat.ChatMessage;
import model.flatmate.FlatMateEntry;
import model.tenant.User;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import repository.ChatMessageRepository;
import repository.FlatMateEntryRepository;
import repository.UserRepository;
import util.DummyDataGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static util.DummyDataGenerator.*;

public class FlatMateEntryServiceTest extends AbstractBaseServiceTest<FlatMateEntry> {

    private FlatMateEntryService flatMateEntryService;

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    private FlatMateEntryService spyService;

    @Override
    protected void setupProxyAndService() {
        repository = mock(FlatMateEntryRepository.class);
        chatMessageRepository = mock(ChatMessageRepository.class);
        userRepository = mock(UserRepository.class);

        spyService = spy(new FlatMateEntryService((FlatMateEntryRepository)repository, chatMessageRepository, userRepository));
        when(repository.findAll())
                .thenReturn(Lists.newArrayList(DummyDataGenerator.generateDummyFlatMateEntry()));

        when(chatMessageRepository.findAll())
                .thenReturn(Lists.newArrayList(DummyDataGenerator.generateDummyChatMessage()));

        User u = generateDummyUser();
        ChatContact chatContact = generateDummyChatContact();
        u.setContacts(Sets.newHashSet(chatContact));

        when(userRepository.findAll())
                .thenReturn(Lists.newArrayList(u));

        AspectJProxyFactory proxy =
                new AspectJProxyFactory(new FlatMateEntryService((FlatMateEntryRepository)repository, chatMessageRepository, userRepository));
        proxy.addAspect(new NullCheckAspect());
        this.service = proxy.getProxy();
        this.flatMateEntryService = proxy.getProxy();
    }

    @Test
    public void testIfAgeOffsetEntriesAreReturnedCorrectly() {
        assertThat(flatMateEntryService.getByAgeRadius(AGE_IN_RADIUS), hasSize(1));
        assertThat(flatMateEntryService.getByAgeRadius(AGE_OUT_OF_RADIUS), hasSize(0));
    }

    @Test
    public void testIfLifestyleCriteriaEntriesReturnedCorrectly() {
        assertThat(flatMateEntryService.getByLifeStyle(LIFESTYLE_CRITERIA.toString()), hasSize(1));
        assertThat(flatMateEntryService.getByLifeStyle("STAY_AT_HOME"), hasSize(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfLifestyleCriteriaEntriesThrowsOnBadInput() {
        assertThat(flatMateEntryService.getByLifeStyle("NOT_EXISTING"), hasSize(0));
    }

    @Test
    public void testIfUltimateMatchesReturnedBasedBothOnLifestyleAndAgeCriteria() {
        assertThat(flatMateEntryService.getUltimateMatches(LIFESTYLE_CRITERIA.toString(), AGE_IN_RADIUS), hasSize(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfUltimateMatchesThrowsOnBadInput() {
        assertThat(flatMateEntryService.getUltimateMatches("NOT_EXISTING", AGE_IN_RADIUS), hasSize(0));
    }

    @Test
    public void testIfMessagesAndContactsAreDeletedForEntries() {
        List<ChatMessage> deletedMsgs = new ArrayList<>();

        doAnswer(invocation -> {
            deletedMsgs.add((ChatMessage)invocation.getArguments()[0]);
            return null;
        }).when(chatMessageRepository).delete(any(ChatMessage.class));

        List<User> users = new ArrayList<>();
        doAnswer(invocation -> {
            users.add((User)invocation.getArguments()[0]);
            return null;
        }).when(userRepository).save(any(User.class));

        spyService.deleteEntryWithConversations(generateDummyFlatMateEntry());

        assertThat(deletedMsgs, hasSize(1));
        assertThat(users, hasSize(1));
        assertThat(users.get(0).getContacts(), empty());
    }
}