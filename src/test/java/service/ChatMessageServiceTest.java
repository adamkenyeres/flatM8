package service;

import aop.NullCheckAspect;
import com.google.common.collect.Lists;
import model.chat.ChatMessage;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import repository.AddMateRequestRepository;
import repository.ChatMessageRepository;
import util.DummyDataGenerator;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChatMessageServiceTest extends AbstractBaseServiceTest<ChatMessage> {

    private ChatMessageService chatMessageService;

    @Override
    protected void setupProxyAndService() {
        repository = mock(ChatMessageRepository.class);

        when(repository.findAll())
                .thenReturn(Lists.newArrayList(DummyDataGenerator.generateDummyChatMessage()));

        AspectJProxyFactory proxy = new AspectJProxyFactory(new ChatMessageService((ChatMessageRepository)repository));
        proxy.addAspect(new NullCheckAspect());
        this.service = proxy.getProxy();
        this.chatMessageService = proxy.getProxy();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIfGetMessagesByReceiverThrowsOnNullInput() {
        this.chatMessageService.getMessagesByReceiver(null);
    }

    @Test
    public void testIfMessagesFetchedCorrectlyForUser() {
        List<ChatMessage> msgs = chatMessageService.getMessagesByReceiver(DummyDataGenerator.generateDummyUser());

        assertThat(msgs, hasSize(1));
        assertThat(msgs.get(0).getSender(), is(DummyDataGenerator.generateDummyUser()));
        assertThat(msgs.get(0).getReceivers(), Matchers.contains(DummyDataGenerator.generateDummyUser()));
    }
}