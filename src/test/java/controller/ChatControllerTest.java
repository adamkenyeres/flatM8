package controller;

import com.google.common.collect.Lists;
import model.chat.ChatMessage;
import model.tenant.User;
import org.glassfish.hk2.runlevel.RunLevelException;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import service.ChatMessageService;
import util.DummyDataGenerator;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ChatControllerTest extends AbstractBaseControllerTest<ChatMessage> {

    private ChatController chatController;
    private ChatController emptyChatController;

    @Override
    public void setUp(){
        this.service = mock(ChatMessageService.class);
        this.emptyService = mock(ChatMessageService.class);
        ChatMessageService chatMessageService = (ChatMessageService)service;
        ChatMessageService emptyChatMessageService = (ChatMessageService)emptyService;

        chatController = new ChatController((ChatMessageService)service);
        emptyChatController = new ChatController((ChatMessageService)emptyService);

        when(emptyService.getAll())
                .thenReturn(Collections.emptyList());
        when(emptyService.getById(nullable(String.class)))
                .thenReturn(null);
        when(emptyService.createOrUpdate(nullable(ChatMessage.class)))
                .thenReturn(null);
        doThrow(new RunLevelException())
                .when(emptyService)
                .deleteById(nullable(String.class));
        doThrow(new RunLevelException())
                .when(emptyService)
                .deleteAll();
        when(emptyChatMessageService.getMessagesByReceiver(nullable(User.class)))
                .thenReturn(Collections.emptyList());

        when(service.getAll())
                .thenReturn(getDummyEntities());
        when(service.getById(nullable(String.class)))
                .thenReturn(getDummyEntities().get(0));
        when(service.createOrUpdate(nullable(ChatMessage.class)))
                .thenReturn(getDummyEntities().get(0));
        doNothing()
                .when(service)
                .deleteById(nullable(String.class));
        doNothing()
                .when(service)
                .deleteAll();
        when(chatMessageService.getMessagesByReceiver(nullable(User.class)))
                .thenReturn(Lists.newArrayList(DummyDataGenerator.generateDummyChatMessage()));

        this.controller = new ChatController((ChatMessageService)service);
        this.emptyController = new ChatController((ChatMessageService)emptyService);
    }

    @Test
    public void testIfGetAllForReceiverReturnCorrectResponse() {
        ResponseEntity responseEntity = chatController.getAllForReceiver(null);
        ResponseEntity responseEntity1 = emptyChatController.getAllForReceiver(null);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(404, responseEntity1.getStatusCode().value());
    }

    @Override
    protected List<ChatMessage> getDummyEntities() {
        return Lists.newArrayList(DummyDataGenerator.generateDummyChatMessage());
    }
}