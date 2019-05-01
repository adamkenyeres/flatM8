package model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.flatmate.FlatMateEntry;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatContact {
    private FlatMateEntry contactEntry;
    private String senderEmail;
}
