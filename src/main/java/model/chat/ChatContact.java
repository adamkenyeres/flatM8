package model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import model.flatmate.FlatMateEntry;
import model.tenant.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatContact {
    private FlatMateEntry contactEntry;
    private String senderEmail;
}
