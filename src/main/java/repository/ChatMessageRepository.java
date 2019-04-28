package repository;

import model.chat.ChatMessage;
import model.flatmate.FlatMateEntry;
import model.tenant.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findAllBySender(User sender);
    List<ChatMessage> findAllByEntry(FlatMateEntry entry);
    List<ChatMessage> findAllByEntryAndSender(FlatMateEntry entry, User sender);
}
