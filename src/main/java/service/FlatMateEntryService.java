package service;

import annotation.ImplicitNullCheck;
import model.chat.ChatMessage;
import model.criteria.LifestyleCriteria;
import model.flat.Flat;
import model.flatmate.FlatMateEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.FlatMateEntryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlatMateEntryService extends AbstractBaseService<FlatMateEntry> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlatMateEntryService.class);
    private final FlatMateEntryRepository repository;
    private final ChatMessageService chatMessageService;
    private final UserService userService;

    @Autowired
    public FlatMateEntryService(FlatMateEntryRepository repository, ChatMessageService chatMessageService, UserService userService) {
        super(repository);
        this.repository = repository;
        this.chatMessageService = chatMessageService;
        this.userService = userService;
    }

    @ImplicitNullCheck
    public List<FlatMateEntry> getAllEntriesForFlat(Flat flat) {
        return repository.findAllByFlat(flat);
    }

    public void deleteAllForFlat(Flat flat) {
        repository.deleteAllByFlat(flat);
    }

    public List<FlatMateEntry> getByAgeRadius(Integer age) {
        return repository.findAll()
                .stream()
                .filter(e ->
                        (e.getRoomCriteria().getCriteria().getAgeCriteria() - e.getRoomCriteria().getCriteria().getAgeOffset()) <= age
                        && (e.getRoomCriteria().getCriteria().getAgeCriteria() + e.getRoomCriteria().getCriteria().getAgeOffset()) >= age)
                .collect(Collectors.toList());
    }

    @ImplicitNullCheck
    public List<FlatMateEntry> getByLifeStyle(String lifestyleCriteria) {
        LifestyleCriteria crit = LifestyleCriteria.valueOf(lifestyleCriteria);

        return repository.findAll()
                .stream()
                .filter(e -> e.getRoomCriteria().getCriteria().getLifestyleCriteria().equals(crit))
                .collect(Collectors.toList());
    }

    @ImplicitNullCheck
    public List<FlatMateEntry> getUltimateMatches(String lifestyleCriteria, Integer age) {
        List<FlatMateEntry> entries = getByAgeRadius(age);
        entries.retainAll(getByLifeStyle(lifestyleCriteria));

        return entries;
    }

    @ImplicitNullCheck
    public void deleteEntryWithConversations(FlatMateEntry entry) {

        delete(entry);
        List<ChatMessage> msgs = chatMessageService.getAll()
                .stream()
                .filter(msg -> msg.getChatContact().getContactEntry().equals(entry))
                .collect(Collectors.toList());

        msgs.forEach(chatMessageService::delete);

        userService.getAll()
                .forEach(u -> {
                    u.getContacts().removeIf(e -> e.getContactEntry().equals(entry));
                    userService.createOrUpdate(u);
                });

    }
}
