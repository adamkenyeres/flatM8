package model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.tenant.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String data;
    private User from;
    private User to;
}
