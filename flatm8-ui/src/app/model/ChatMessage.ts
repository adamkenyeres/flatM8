import {User} from "./User";
import {FlatMateEntry} from "./FlatMateEntry";
import {ChatContact} from "./ChatContact";

export class ChatMessage {
  chatContact: ChatContact;
  sender: User;
  message: string;
  timestamp: Date;
  receivers: Array<User> = [];
}
