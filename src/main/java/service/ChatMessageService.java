package service;

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

    public List<ChatMessage> getMessagesBySender(User sender){
        if (sender == null) {
            throw new IllegalArgumentException("User can't be null!");
        }

        return repository.findAllBySender(sender);
    }

    public List<ChatMessage> getMessagesByEntry(FlatMateEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("Entry can't be null!");
        }

        return repository.findAllByEntry(entry);
    }

    public List<ChatMessage> getMessagesByEntryAndSender(FlatMateEntry entry, User sender) {
        if (entry == null || sender == null) {
            throw new IllegalArgumentException("Entry and User can't be null!");
        }

        return repository.findAllByEntryAndSender(entry, sender);
    }

    public List<ChatMessage> getMessagesByReceiver(User u) {
        return repository.findAll()
                .stream()
                .filter(msg -> msg.getReceivers().contains(u))
                .collect(Collectors.toList());
    }
}
