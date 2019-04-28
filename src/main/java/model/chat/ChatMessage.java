package model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.flatmate.FlatMateEntry;
import model.tenant.User;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String message;
    private FlatMateEntry entry;
    private User sender;
    private Date timestamp;
    private Set<User> receivers;
}
