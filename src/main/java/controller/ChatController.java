package controller;

import model.chat.ChatMessage;
import model.tenant.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.AbstractBaseService;
import service.ChatMessageService;

import java.util.List;

@RequestMapping("/chatMessages")
@RestController
public class ChatController extends AbstractBaseController<ChatMessage> {

    private ChatMessageService chatMessageService;

    @Autowired
    public ChatController(ChatMessageService chatMessageService) {
        super(chatMessageService);
        this.chatMessageService = chatMessageService;
    }

    @RequestMapping(value = "/getAllForSender", method = RequestMethod.POST)
    public ResponseEntity getAllForSender(@RequestBody User user) {
        List<ChatMessage> messages = chatMessageService.getMessagesBySender(user);

        if (messages.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(messages);
        }
    }

    @RequestMapping(value = "/getAllForReciever", method = RequestMethod.POST)
    public ResponseEntity getAllForReceiver(@RequestBody User user) {
        List<ChatMessage> messages = chatMessageService.getMessagesByReceiver(user);

        if (messages.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(messages);
        }
    }
}
