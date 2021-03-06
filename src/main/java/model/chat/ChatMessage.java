package model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.tenant.User;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private ChatContact chatContact;
    private String message;
    private Date timestamp;
    private User sender;
    private Set<User> receivers;
}
