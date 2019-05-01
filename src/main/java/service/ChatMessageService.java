package service;

import annotation.ImplicitNullCheck;
import model.chat.ChatMessage;
import model.flatmate.FlatMateEntry;
import model.tenant.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import repository.ChatMessageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageService extends AbstractBaseService<ChatMessage> {

    private ChatMessageRepository repository;

    @Autowired
    public ChatMessageService(ChatMessageRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @ImplicitNullCheck
    public List<ChatMessage> getMessagesByReceiver(User u) {
        return repository.findAll()
                .stream()
                .filter(msg -> msg.getReceivers().contains(u))
                .collect(Collectors.toList());
    }
}
